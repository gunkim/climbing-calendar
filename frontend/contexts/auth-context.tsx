"use client"

import type React from "react"
import {createContext, useContext, useEffect, useState} from "react"
import type {AuthState, User} from "@/types/auth"

interface AuthContextType extends AuthState {
    loginWithKakao: (token: string) => Promise<boolean>
    logout: () => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export function AuthProvider({children}: { children: React.ReactNode }) {
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
    const loginWithKakao = async (token: string): Promise<boolean> => {
        try {
            const response = await fetch("http://localhost:8080/api/v1/users/me", {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            })

            if (!response.ok) {
                throw new Error("Failed to fetch user data")
            }

            const user: User = await response.json()

            localStorage.setItem("climbingUser", JSON.stringify(user))

            setAuthState({
                user,
                isAuthenticated: true,
            })

            return true
        } catch (error) {
            console.error("Login failed:", error)
            return false
        }
    }

    // 로그아웃 함수
    const logout = () => {
        localStorage.removeItem("token")
        localStorage.removeItem("climbingUser")
        setAuthState({
            user: null,
            isAuthenticated: false,
        })
    }

    return <AuthContext.Provider value={{...authState, loginWithKakao, logout}}>{children}</AuthContext.Provider>
}

export function useAuth() {
    const context = useContext(AuthContext)
    if (context === undefined) {
        throw new Error("useAuth must be used within an AuthProvider")
    }
    return context
}

