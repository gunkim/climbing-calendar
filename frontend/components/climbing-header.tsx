"use client"

import { MountainSnow, LogOut, User, BarChart2 } from "lucide-react"
import Link from "next/link"
import { useAuth } from "@/contexts/auth-context"
import { Button } from "@/components/ui/button"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"

export function ClimbingHeader() {
  const { user, isAuthenticated, logout } = useAuth()

  const getInitials = (name: string) => {
    return name
      .split(" ")
      .map((part) => part.charAt(0))
      .join("")
      .toUpperCase()
  }

  return (
    <header className="border-b border-gray-100 bg-white shadow-sm">
      <div className="container mx-auto flex h-16 items-center justify-between px-4">
        <Link href="/" className="flex items-center gap-2 font-bold text-gray-800">
          <MountainSnow className="h-6 w-6 text-sky-500" />
          <span>클라이밍 캘린더</span>
        </Link>
        <nav className="flex items-center gap-4">
          {isAuthenticated ? (
            <>
              <Link
                href="/difficulty-comparison"
                className="text-sm font-medium text-gray-600 hover:text-gray-900 transition-colors"
              >
                <div className="flex items-center gap-1">
                  <BarChart2 className="h-4 w-4" />
                  <span className="hidden sm:inline">난이도 비교</span>
                </div>
              </Link>
              <Link
                href="/events/new"
                className="rounded-md bg-sky-500 px-4 py-2 text-sm font-medium text-white hover:bg-sky-600 transition-colors"
              >
                새 세션 추가
              </Link>
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button variant="ghost" className="relative h-8 w-8 rounded-full hover:bg-gray-100">
                    <Avatar className="h-8 w-8 border border-gray-200">
                      {user?.profileImage ? (
                        <AvatarImage src={user.profileImage} alt={user.name} />
                      ) : (
                        <AvatarFallback className="bg-sky-100 text-sky-700">
                          {user?.name ? getInitials(user.name) : "U"}
                        </AvatarFallback>
                      )}
                    </Avatar>
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent align="end" className="bg-white border-gray-200">
                  <DropdownMenuLabel>내 계정</DropdownMenuLabel>
                  <DropdownMenuSeparator className="bg-gray-200" />
                  <DropdownMenuItem asChild className="hover:bg-gray-100 focus:bg-gray-100 cursor-pointer">
                    <Link href="/profile">
                      <User className="mr-2 h-4 w-4 text-sky-500" />
                      <span>프로필</span>
                    </Link>
                  </DropdownMenuItem>
                  <DropdownMenuItem onClick={logout} className="hover:bg-gray-100 focus:bg-gray-100 cursor-pointer">
                    <LogOut className="mr-2 h-4 w-4 text-sky-500" />
                    <span>로그아웃</span>
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </>
          ) : (
            <Link
              href="/login"
              className="rounded-md bg-[#FEE500] px-4 py-2 text-sm font-medium text-black hover:bg-[#FEE500]/90"
            >
              카카오 로그인
            </Link>
          )}
        </nav>
      </div>
    </header>
  )
}

