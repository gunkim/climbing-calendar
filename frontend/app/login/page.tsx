"use client"

import { useState } from "react"
import { useRouter } from "next/navigation"
import Link from "next/link"
import { MountainSnow } from "lucide-react"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { useAuth } from "@/contexts/auth-context"
import { useToast } from "@/components/ui/use-toast"

export default function LoginPage() {
  const router = useRouter()
  const { loginWithKakao } = useAuth()
  const { toast } = useToast()
  const [isLoggingIn, setIsLoggingIn] = useState(false)

  // 카카오 로그인 처리
  const handleKakaoLogin = async () => {
    setIsLoggingIn(true)

    try {
      const success = await loginWithKakao()
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

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-background p-4">
      <div className="flex items-center mb-8">
        <MountainSnow className="h-8 w-8 mr-2" />
        <h1 className="text-2xl font-bold">클라이밍 캘린더</h1>
      </div>

      <Card className="w-full max-w-md">
        <CardHeader>
          <CardTitle>로그인</CardTitle>
          <CardDescription>카카오 계정으로 클라이밍 캘린더에 로그인하세요.</CardDescription>
        </CardHeader>
        <CardContent className="flex flex-col items-center">
          <button
            onClick={handleKakaoLogin}
            disabled={isLoggingIn}
            className="w-full bg-[#FEE500] hover:bg-[#FEE500]/90 text-[#000000] font-medium py-3 px-4 rounded-md flex items-center justify-center transition-colors"
          >
            {isLoggingIn ? (
              "로그인 중..."
            ) : (
              <>
                <svg className="w-5 h-5 mr-2" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M12 3C6.48 3 2 6.48 2 11c0 2.66 1.46 5.03 3.77 6.44l-1.2 4.04c-.12.41.31.75.67.54l4.86-2.83c.6.12 1.23.18 1.9.18 5.52 0 10-3.48 10-8s-4.48-8-10-8z" />
                </svg>
                카카오 계정으로 로그인
              </>
            )}
          </button>

          <p className="mt-6 text-sm text-muted-foreground">
            로그인함으로써 클라이밍 캘린더의{" "}
            <Link href="#" className="text-primary hover:underline">
              서비스 약관
            </Link>
            과{" "}
            <Link href="#" className="text-primary hover:underline">
              개인정보 처리방침
            </Link>
            에 동의합니다.
          </p>
        </CardContent>
      </Card>

      <p className="mt-4 text-sm text-muted-foreground">
        <Link href="/" className="text-primary hover:underline">
          홈으로 돌아가기
        </Link>
      </p>
    </div>
  )
}

