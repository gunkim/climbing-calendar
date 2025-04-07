"use client"
import {useAuth} from "@/contexts/auth-context"
import {CalendarView} from "@/components/calendar-view"
import {ClimbingHeader} from "@/components/climbing-header"
import {useEffect, useState} from "react"
import {useRouter} from "next/navigation"
import {useToast} from "@/components/ui/use-toast"
import {UnauthenticatedView} from "@/components/unauthenticated-view";

const LOADING_MESSAGE = "데이터를 불러오고 있어요..."
const SUCCESS_TOAST = {
    title: "로그인 성공",
    description: "클라이밍 캘린더에 오신 것을 환영합니다!",
}
const FAILURE_TOAST = {
    title: "로그인 실패",
    description: "카카오 로그인 중 문제가 발생했습니다.",
    variant: "destructive",
}
const ERROR_TOAST = {
    title: "오류 발생",
    description: "로그인 중 문제가 발생했습니다. 다시 시도해주세요.",
    variant: "destructive",
}

export default function HomePage() {
    const {loginWithKakao, isAuthenticated, isLoading} = useAuth()
    const router = useRouter()
    const {toast} = useToast()
    const [isLoggingIn, setIsLoggingIn] = useState(false)

    const showNotification = (type: "success" | "failure" | "error") => {
        const toastConfig = type === "success"
            ? SUCCESS_TOAST
            : type === "failure"
                ? FAILURE_TOAST
                : ERROR_TOAST

        toast(toastConfig)
    }

    const handleKakaoLogin = async (token: string) => {
        setIsLoggingIn(true)
        try {
            const success = await loginWithKakao(token)
            if (success) {
                showNotification("success")
                router.push("/")
            } else {
                showNotification("failure")
            }
        } catch (error) {
            showNotification("error")
        } finally {
            setIsLoggingIn(false)
        }
    }

    const handleTokenFromURL = (token: string, searchParams: URLSearchParams) => {
        localStorage.setItem("token", token)

        searchParams.delete("token")
        const newPath = `${window.location.pathname}?${searchParams.toString()}`
        router.replace(newPath.endsWith("?") ? window.location.pathname : newPath)
    }

    useEffect(() => {
        const searchParams = new URLSearchParams(window.location.search)
        const token = searchParams.get("token")
        if (token) {
            (async () => await handleKakaoLogin(token))()
            handleTokenFromURL(token, searchParams)
        }
    }, [router])

    if (isLoading || isLoggingIn) {
        return (
            <div className="min-h-screen flex flex-col items-center justify-center bg-gray-50">
                <div className="animate-spin rounded-full border-t-4 border-b-4 border-gray-900 h-12 w-12 mb-4"></div>
                <p className="text-gray-700 text-lg font-medium">{LOADING_MESSAGE}</p>
            </div>
        )
    }

    return (
        <div className="min-h-screen bg-white text-gray-900">
            <ClimbingHeader/>
            <main className="container mx-auto py-6 px-4">
                {isAuthenticated ? <CalendarView/> : <UnauthenticatedView/>}
            </main>
        </div>
    )
}
