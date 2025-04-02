"use client"

import { useAuth } from "@/contexts/auth-context"
import { CalendarView } from "@/components/calendar-view"
import { ClimbingHeader } from "@/components/climbing-header"
import { Button } from "@/components/ui/button"
import { MountainSnow } from "lucide-react"
import Link from "next/link"

export default function HomePage() {
  const { isAuthenticated } = useAuth()

  return (
    <div className="min-h-screen bg-white text-gray-900">
      <ClimbingHeader />
      <main className="container mx-auto py-6 px-4">
        {isAuthenticated ? (
          <CalendarView />
        ) : (
          <div className="flex flex-col items-center justify-center py-12 text-center">
            <MountainSnow className="h-16 w-16 mb-6 text-sky-500" />
            <h1 className="text-4xl font-bold mb-4">클라이밍 캘린더</h1>
            <p className="text-xl mb-8 text-gray-600">클라이밍 세션을 기록하고 관리하는 최고의 방법</p>
            <Link href="/login">
              <Button className="bg-[#FEE500] text-black hover:bg-[#FEE500]/90 flex items-center gap-2 px-6 py-5 text-base">
                <svg className="w-5 h-5" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M12 3C6.48 3 2 6.48 2 11c0 2.66 1.46 5.03 3.77 6.44l-1.2 4.04c-.12.41.31.75.67.54l4.86-2.83c.6.12 1.23.18 1.9.18 5.52 0 10-3.48 10-8s-4.48-8-10-8z" />
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

