"use client"

import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import { ClimbingHeader } from "@/components/climbing-header"
import { useAuth } from "@/contexts/auth-context"
import { useClimbingEvents } from "@/hooks/use-climbing-events"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { MountainSnow, Route, Dumbbell, Calendar, BarChart2, MapPin } from "lucide-react"
import { format, parseISO, isThisMonth, isThisYear } from "date-fns"
import { ko } from "date-fns/locale"
import { getDifficultySystemByGymName, type DifficultyColor } from "@/data/gym-difficulty-systems"

export default function ProfilePage() {
  const router = useRouter()
  const { user, isAuthenticated } = useAuth()
  const { events } = useClimbingEvents()
  const [activeTab, setActiveTab] = useState("overview")

  // 인증 확인
  useEffect(() => {
    if (!isAuthenticated) {
      router.push("/login")
    }
  }, [isAuthenticated, router])

  if (!isAuthenticated || !user) {
    return null
  }

  // 이벤트 통계 계산
  const totalEvents = events.length
  const thisMonthEvents = events.filter((event) => isThisMonth(parseISO(event.date)))
  const thisYearEvents = events.filter((event) => isThisYear(parseISO(event.date)))

  // 암장별 방문 횟수
  const gymVisits: Record<string, number> = {}
  events.forEach((event) => {
    gymVisits[event.location] = (gymVisits[event.location] || 0) + 1
  })

  // 방문 횟수 기준 정렬된 암장 목록
  const sortedGyms = Object.entries(gymVisits)
    .sort((a, b) => b[1] - a[1])
    .slice(0, 5)

  // 클라이밍 유형별 세션 수
  const sessionsByType: Record<string, number> = {
    bouldering: 0,
    lead: 0,
    training: 0,
  }

  events.forEach((event) => {
    sessionsByType[event.type] = (sessionsByType[event.type] || 0) + 1
  })

  // 난이도별 클리어 횟수 합계
  const totalDifficulties: Record<string, number> = {}

  events.forEach((event) => {
    if (event.difficulties) {
      Object.entries(event.difficulties).forEach(([color, count]) => {
        if (count) {
          totalDifficulties[color] = (totalDifficulties[color] || 0) + count
        }
      })
    }
  })

  // 난이도별 클리어 횟수를 내림차순으로 정렬
  const sortedDifficulties = Object.entries(totalDifficulties).sort((a, b) => b[1] - a[1])

  // 사용자 이니셜 가져오기
  const getInitials = (name: string) => {
    return name
      .split(" ")
      .map((part) => part.charAt(0))
      .join("")
      .toUpperCase()
  }

  // 난이도 색상 정보 가져오기
  const getDifficultyColor = (name: string): DifficultyColor | undefined => {
    // 모든 암장의 난이도 시스템에서 해당 이름의 난이도 찾기
    for (const gym of Object.keys(gymVisits)) {
      const system = getDifficultySystemByGymName(gym)
      const diffColor = system.difficulties.find((d) => d.name === name)
      if (diffColor) return diffColor
    }

    // 기본 시스템에서 찾기
    const defaultSystem = getDifficultySystemByGymName("")
    return defaultSystem.difficulties.find((d) => d.name === name)
  }

  return (
    <div className="min-h-screen bg-background">
      <ClimbingHeader />
      <main className="container mx-auto py-6 px-4">
        <div className="max-w-4xl mx-auto">
          <div className="flex flex-col md:flex-row items-start gap-6 mb-8">
            <div className="w-full md:w-1/3">
              <Card>
                <CardContent className="pt-6">
                  <div className="flex flex-col items-center">
                    <Avatar className="h-24 w-24 mb-4">
                      {user.profileImage ? (
                        <AvatarImage src={user.profileImage} alt={user.name} />
                      ) : (
                        <AvatarFallback className="text-2xl">{getInitials(user.name)}</AvatarFallback>
                      )}
                    </Avatar>
                    <h2 className="text-xl font-bold">{user.name}</h2>
                    <p className="text-muted-foreground">{user.email}</p>

                    <div className="w-full mt-6 grid grid-cols-3 gap-2 text-center">
                      <div className="p-3 bg-blue-50 rounded-lg">
                        <p className="text-2xl font-bold text-blue-700">{totalEvents}</p>
                        <p className="text-xs text-blue-600">전체</p>
                      </div>
                      <div className="p-3 bg-green-50 rounded-lg">
                        <p className="text-2xl font-bold text-green-700">{thisMonthEvents.length}</p>
                        <p className="text-xs text-green-600">이번 달</p>
                      </div>
                      <div className="p-3 bg-purple-50 rounded-lg">
                        <p className="text-2xl font-bold text-purple-700">{thisYearEvents.length}</p>
                        <p className="text-xs text-purple-600">올해</p>
                      </div>
                    </div>
                  </div>
                </CardContent>
              </Card>
            </div>

            <div className="w-full md:w-2/3">
              <Tabs value={activeTab} onValueChange={setActiveTab} className="w-full">
                <TabsList className="grid grid-cols-2 mb-4">
                  <TabsTrigger value="overview" className="flex items-center gap-2">
                    <BarChart2 className="h-4 w-4" />
                    <span>통계 개요</span>
                  </TabsTrigger>
                  <TabsTrigger value="history" className="flex items-center gap-2">
                    <Calendar className="h-4 w-4" />
                    <span>최근 기록</span>
                  </TabsTrigger>
                </TabsList>

                <TabsContent value="overview">
                  <div className="space-y-4">
                    <Card>
                      <CardHeader>
                        <CardTitle className="text-lg">클라이밍 유형</CardTitle>
                      </CardHeader>
                      <CardContent>
                        <div className="grid grid-cols-3 gap-4">
                          <div className="flex flex-col items-center p-3 bg-blue-50 rounded-lg">
                            <MountainSnow className="h-6 w-6 text-blue-600 mb-2" />
                            <p className="text-xl font-bold text-blue-700">{sessionsByType.bouldering}</p>
                            <p className="text-xs text-blue-600">볼더링</p>
                          </div>
                          <div className="flex flex-col items-center p-3 bg-green-50 rounded-lg">
                            <Route className="h-6 w-6 text-green-600 mb-2" />
                            <p className="text-xl font-bold text-green-700">{sessionsByType.lead}</p>
                            <p className="text-xs text-green-600">리드</p>
                          </div>
                          <div className="flex flex-col items-center p-3 bg-purple-50 rounded-lg">
                            <Dumbbell className="h-6 w-6 text-purple-600 mb-2" />
                            <p className="text-xl font-bold text-purple-700">{sessionsByType.training}</p>
                            <p className="text-xs text-purple-600">훈련</p>
                          </div>
                        </div>
                      </CardContent>
                    </Card>

                    <Card>
                      <CardHeader>
                        <CardTitle className="text-lg">자주 방문한 암장</CardTitle>
                      </CardHeader>
                      <CardContent>
                        <div className="space-y-3">
                          {sortedGyms.map(([gym, count], index) => (
                            <div key={gym} className="flex items-center justify-between">
                              <div className="flex items-center">
                                <div className="w-6 h-6 rounded-full bg-blue-100 text-blue-700 flex items-center justify-center text-xs font-bold mr-3">
                                  {index + 1}
                                </div>
                                <span>{gym}</span>
                              </div>
                              <span className="font-semibold">{count}회</span>
                            </div>
                          ))}
                        </div>
                      </CardContent>
                    </Card>

                    <Card>
                      <CardHeader>
                        <CardTitle className="text-lg">난이도별 클리어 횟수</CardTitle>
                      </CardHeader>
                      <CardContent>
                        <div className="grid grid-cols-2 sm:grid-cols-3 gap-3">
                          {sortedDifficulties.map(([color, count]) => {
                            const diffColor = getDifficultyColor(color)
                            if (!diffColor || count === 0) return null

                            return (
                              <div
                                key={color}
                                className={`flex items-center justify-between p-2 rounded-lg ${diffColor.bgColor} ${diffColor.textColor} border ${diffColor.borderColor}`}
                              >
                                <span className="font-medium">{diffColor.label}</span>
                                <span className="font-bold">{count}개</span>
                              </div>
                            )
                          })}
                        </div>
                      </CardContent>
                    </Card>
                  </div>
                </TabsContent>

                <TabsContent value="history">
                  <Card>
                    <CardHeader>
                      <CardTitle className="text-lg">최근 클라이밍 기록</CardTitle>
                    </CardHeader>
                    <CardContent>
                      <div className="space-y-4">
                        {events.length === 0 ? (
                          <p className="text-center text-muted-foreground py-8">
                            아직 기록된 클라이밍 세션이 없습니다.
                          </p>
                        ) : (
                          events
                            .sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime())
                            .slice(0, 10)
                            .map((event) => {
                              const eventDate = parseISO(event.date)
                              const eventType =
                                event.type === "bouldering"
                                  ? { icon: <MountainSnow className="h-4 w-4" />, label: "볼더링" }
                                  : event.type === "lead"
                                    ? { icon: <Route className="h-4 w-4" />, label: "리드" }
                                    : { icon: <Dumbbell className="h-4 w-4" />, label: "훈련" }

                              // 해당 암장의 난이도 시스템 가져오기
                              const diffSystem = getDifficultySystemByGymName(event.location)

                              // 클리어한 난이도 개수 계산
                              const clearedDifficulties = Object.entries(event.difficulties || {})
                                .filter(([_, count]) => count && count > 0)
                                .map(([color, count]) => {
                                  const diffColor = diffSystem.difficulties.find((d) => d.name === color)
                                  return diffColor ? { ...diffColor, count } : null
                                })
                                .filter(Boolean)

                              return (
                                <div key={event.id} className="border rounded-lg p-4">
                                  <div className="flex justify-between items-start mb-2">
                                    <h3 className="font-semibold">{event.title}</h3>
                                    <div className="flex items-center text-sm text-muted-foreground">
                                      {format(eventDate, "yyyy.MM.dd", { locale: ko })}
                                    </div>
                                  </div>

                                  <div className="flex items-center text-sm text-muted-foreground mb-2">
                                    <div className="flex items-center mr-4">
                                      {eventType.icon}
                                      <span className="ml-1">{eventType.label}</span>
                                    </div>
                                    <div className="flex items-center">
                                      <MapPin className="h-4 w-4 mr-1" />
                                      <span>{event.location}</span>
                                    </div>
                                  </div>

                                  {clearedDifficulties.length > 0 && (
                                    <div className="flex flex-wrap gap-2 mt-2">
                                      {clearedDifficulties.map((diff, idx) => (
                                        <div
                                          key={idx}
                                          className={`text-xs px-2 py-1 rounded-full ${diff.bgColor} ${diff.textColor} border ${diff.borderColor}`}
                                        >
                                          {diff.label} {diff.count}개
                                        </div>
                                      ))}
                                    </div>
                                  )}

                                  {event.notes && <p className="text-sm mt-2 text-muted-foreground">{event.notes}</p>}
                                </div>
                              )
                            })
                        )}
                      </div>
                    </CardContent>
                  </Card>
                </TabsContent>
              </Tabs>
            </div>
          </div>
        </div>
      </main>
    </div>
  )
}

