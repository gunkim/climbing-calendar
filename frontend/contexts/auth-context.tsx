"use client"

import type React from "react"

import { createContext, useContext, useEffect, useState } from "react"
import type { User, AuthState } from "@/types/auth"

interface AuthContextType extends AuthState {
  loginWithKakao: () => Promise<boolean>
  logout: () => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [authState, setAuthState] = useState<AuthState>({
    user: null,
    isAuthenticated: false,
  })

  // 페이지 로드 시 로컬 스토리지에서 사용자 정보 가져오기
  useEffect(() => {
    const storedUser = localStorage.getItem("climbingUser")
    if (storedUser) {
      try {
        const user = JSON.parse(storedUser)
        setAuthState({
          user,
          isAuthenticated: true,
        })
      } catch (error) {
        console.error("Failed to parse stored user:", error)
        localStorage.removeItem("climbingUser")
      }
    }
  }, [])

  // 카카오 로그인 함수
  const loginWithKakao = async (): Promise<boolean> => {
    // 실제 애플리케이션에서는 카카오 OAuth 인증 과정을 구현해야 함
    // 여기서는 데모를 위해 모의 구현
    return new Promise((resolve) => {
      setTimeout(() => {
        // 카카오 로그인 성공 시뮬레이션
        const user: User = {
          id: Math.random().toString(36).substring(2, 9),
          email: "user@example.com",
          name: "카카오 사용자",
          profileImage: "https://via.placeholder.com/150",
          provider: "kakao",
        }

        localStorage.setItem("climbingUser", JSON.stringify(user))

        setAuthState({
          user,
          isAuthenticated: true,
        })
        resolve(true)
      }, 1000) // 실제 API 호출을 시뮬레이션하기 위한 지연
    })
  }

  // 로그아웃 함수
  const logout = () => {
    localStorage.removeItem("climbingUser")
    setAuthState({
      user: null,
      isAuthenticated: false,
    })
  }

  return <AuthContext.Provider value={{ ...authState, loginWithKakao, logout }}>{children}</AuthContext.Provider>
}

export function useAuth() {
  const context = useContext(AuthContext)
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider")
  }
  return context
}

