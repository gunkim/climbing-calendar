"use client"

import {useAuth} from "@/contexts/auth-context"
import {CalendarView} from "@/components/calendar-view"
import {ClimbingHeader} from "@/components/climbing-header"
import {Button} from "@/components/ui/button"
import {MountainSnow} from "lucide-react"
import Link from "next/link"
import {useEffect, useState} from "react"
import {useRouter} from "next/navigation"
import { useToast } from "@/components/ui/use-toast"

export default function HomePage() {
    const {isAuthenticated, isLoading} = useAuth() // isAuthLoading 추가
    const {loginWithKakao} = useAuth()
    const router = useRouter()
    const { toast } = useToast()
    const [isLoggingIn, setIsLoggingIn] = useState(false)

    const handleKakaoLogin = async (token: string) => {
        setIsLoggingIn(true)

        try {
            const success = await loginWithKakao(token)
            if (success) {
                toast({
                    title: "로그인 성공",
                    description: "클라이밍 캘린더에 오신 것을 환영합니다!",
                })
                router.push("/")
            } else {
                toast({
                    title: "로그인 실패",
                    description: "카카오 로그인 중 문제가 발생했습니다.",
                    variant: "destructive",
                })
            }
        } catch (error) {
            toast({
                title: "오류 발생",
                description: "로그인 중 문제가 발생했습니다. 다시 시도해주세요.",
                variant: "destructive",
            })
        } finally {
            setIsLoggingIn(false)
        }
    }

    useEffect(() => {
        const searchParams = new URLSearchParams(window.location.search)
        const token = searchParams.get("token")
        if (token) {
            (async () => {
                await handleKakaoLogin(token)
            })()
            localStorage.setItem("token", token)
            searchParams.delete("token")

            const newPath = `${window.location.pathname}?${searchParams.toString()}`
            router.replace(newPath.endsWith("?") ? window.location.pathname : newPath)
        }
    }, [router])

    // 초기 로딩 상태 처리
    if (isLoading || isLoggingIn) {
        return (
            <div className="min-h-screen flex flex-col items-center justify-center bg-gray-50">
                <div className="animate-spin rounded-full border-t-4 border-b-4 border-gray-900 h-12 w-12 mb-4"></div>
                <p className="text-gray-700 text-lg font-medium">데이터를 불러오고 있어요...</p>
            </div>
        )
    }

    return (
        <div className="min-h-screen bg-white text-gray-900">
            <ClimbingHeader/>
            <main className="container mx-auto py-6 px-4">
                {isAuthenticated ? (
                    <CalendarView/>
                ) : (
                    <div className="flex flex-col items-center justify-center py-12 text-center">
                        <MountainSnow className="h-16 w-16 mb-6 text-sky-500"/>
                        <h1 className="text-4xl font-bold mb-4">클라이밍 캘린더</h1>
                        <p className="text-xl mb-8 text-gray-600">클라이밍 세션을 기록하고 관리하는 최고의 방법</p>
                        <Link href="/login">
                            <Button
                                className="bg-[#FEE500] text-black hover:bg-[#FEE500]/90 flex items-center gap-2 px-6 py-5 text-base">
                                <svg className="w-5 h-5" viewBox="0 0 24 24" fill="currentColor">
                                    <path
                                        d="M12 3C6.48 3 2 6.48 2 11c0 2.66 1.46 5.03 3.77 6.44l-1.2 4.04c-.12.41.31.75.67.54l4.86-2.83c.6.12 1.23.18 1.9.18 5.52 0 10-3.48 10-8s-4.48-8-10-8z"/>
                                </svg>
                                카카오 계정으로 시작하기
                            </Button>
                        </Link>
                    </div>
                )}
            </main>
        </div>
    )
}