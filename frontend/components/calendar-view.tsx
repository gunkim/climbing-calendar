"use client"

import {useState} from "react"
import {CalendarIcon, ChevronLeft, ChevronRight, Clock, MapPin, Plus} from "lucide-react"
import {
    addMonths,
    eachDayOfInterval,
    endOfMonth,
    format,
    getDay,
    isSameDay,
    isSameMonth,
    isToday,
    parseISO,
    startOfMonth,
    subMonths,
} from "date-fns"
import {ko} from "date-fns/locale"
import {Button} from "@/components/ui/button"
import {Dialog, DialogContent, DialogHeader, DialogTitle} from "@/components/ui/dialog"
import {EventForm} from "@/components/event-form"
import {useClimbingEvents} from "@/hooks/use-climbing-events"
import {Tooltip, TooltipContent, TooltipProvider, TooltipTrigger} from "@/components/ui/tooltip"
import {AnimatePresence, motion} from "framer-motion"
import {getDifficultySystemByGymName} from "@/data/gym-difficulty-systems"
import {GetScheduleResponse} from "@/apis/climbing-gym";
import Colors from "@/apis/levels";

// 단일 색상 베이스의 파스텔 색상 정의 (라이트 테마)
const pastelShades = {
    lightest: "bg-sky-50 text-sky-700",
    lighter: "bg-sky-100 text-sky-700",
    light: "bg-sky-200 text-sky-800",
    medium: "bg-purple-100 text-purple-700",
    dark: "bg-pink-100 text-pink-700",
    darker: "bg-indigo-100 text-indigo-700",
    darkest: "bg-violet-100 text-violet-700",
}

// 요일별 색상 정의 (파스텔 테마)
const dayColors = [
    pastelShades.medium, // 일요일
    pastelShades.lighter, // 월요일
    pastelShades.lighter, // 화요일
    pastelShades.lighter, // 수요일
    pastelShades.lighter, // 목요일
    pastelShades.lighter, // 금요일
    pastelShades.light, // 토요일
]

// 이벤트 타입별 색상 및 아이콘 정의
const eventTypeStyles = {
    bouldering: {
        bg: "bg-sky-100",
        text: "text-sky-700",
        border: "border-sky-300",
        icon: "🧗",
        label: "볼더링",
    },
    lead: {
        bg: "bg-purple-100",
        text: "text-purple-700",
        border: "border-purple-300",
        icon: "🧗‍♀️",
        label: "리드",
    },
    training: {
        bg: "bg-amber-100",
        text: "text-amber-700",
        border: "border-amber-300",
        icon: "💪",
        label: "훈련",
    },
}

export function CalendarView() {
    const [currentMonth, setCurrentMonth] = useState(new Date())
    const [selectedDate, setSelectedDate] = useState<Date | null>(null)
    const [isDialogOpen, setIsDialogOpen] = useState(false)
    const [selectedEvent, setSelectedEvent] = useState<GetScheduleResponse | null>(null)
    const [animationDirection, setAnimationDirection] = useState<"left" | "right">("right")

    const {events, addEvent, updateEvent, deleteEvent} = useClimbingEvents()

    const monthStart = startOfMonth(currentMonth)
    const monthEnd = endOfMonth(currentMonth)
    const days = eachDayOfInterval({start: monthStart, end: monthEnd})

    const nextMonth = () => {
        setAnimationDirection("left")
        setCurrentMonth(addMonths(currentMonth, 1))
    }

    const prevMonth = () => {
        setAnimationDirection("right")
        setCurrentMonth(subMonths(currentMonth, 1))
    }

    const handleDateClick = (day: Date) => {
        setSelectedDate(day)
        setSelectedEvent(null)
        setIsDialogOpen(true)
    }

    const handleEventClick = (event: GetScheduleResponse) => {
        setSelectedEvent(event)
        setSelectedDate(new Date(event.scheduleDate))
        setIsDialogOpen(true)
    }

    const getEventsForDay = (day: Date) => {
        return events.filter((event) => isSameDay(new Date(event.scheduleDate), day))
    }

    // 일자별 난이도 클리어 횟수 합계 계산
    const getDifficultyCountsForDay = (events: GetScheduleResponse[]) => {
        const counts: Record<string, number> = {}

        events.forEach((event) => {
            if (event.clearList) {
                event.clearList.forEach((clearItem) => {
                    counts[clearItem.color] = (counts[clearItem.color] || 0) + (clearItem?.count || 0)
                })
            }
        })
        return counts
    }

    // 요일 이름 배열
    const weekDays = ["일", "월", "화", "수", "목", "금", "토"]

    return (
        <div className="space-y-6">
            {/* 헤더 섹션 */}
            <div className="flex items-center justify-between">
                <AnimatePresence mode="wait">
                    <motion.h1
                        key={format(currentMonth, "yyyy-MM")}
                        className="text-3xl font-bold text-gray-800"
                        initial={{opacity: 0, y: 10}}
                        animate={{opacity: 1, y: 0}}
                        exit={{opacity: 0, y: -10}}
                        transition={{duration: 0.3}}
                    >
                        {format(currentMonth, "yyyy년 MMMM", {locale: ko})}
                    </motion.h1>
                </AnimatePresence>
                <div className="flex items-center gap-2">
                    <Button
                        variant="outline"
                        size="icon"
                        onClick={prevMonth}
                        className="rounded-full hover:bg-sky-50 transition-colors border-sky-200 text-sky-600"
                    >
                        <ChevronLeft className="h-5 w-5"/>
                    </Button>
                    <Button
                        variant="outline"
                        size="icon"
                        onClick={() => setCurrentMonth(new Date())}
                        className="rounded-full hover:bg-sky-50 transition-colors border-sky-200 text-sky-600"
                    >
                        <CalendarIcon className="h-5 w-5"/>
                    </Button>
                    <Button
                        variant="outline"
                        size="icon"
                        onClick={nextMonth}
                        className="rounded-full hover:bg-sky-50 transition-colors border-sky-200 text-sky-600"
                    >
                        <ChevronRight className="h-5 w-5"/>
                    </Button>
                </div>
            </div>

            {/* 요일 헤더 */}
            <div className="grid grid-cols-7 gap-1 text-center font-medium rounded-lg overflow-hidden">
                {weekDays.map((day, index) => (
                    <div key={day} className={`py-2 ${dayColors[index]} font-medium`}>
                        {day}
                    </div>
                ))}
            </div>

            {/* 캘린더 그리드 */}
            <AnimatePresence mode="wait">
                <motion.div
                    key={format(currentMonth, "yyyy-MM")}
                    className="grid grid-cols-7 gap-1"
                    initial={{
                        opacity: 0,
                        x: animationDirection === "right" ? 50 : -50,
                    }}
                    animate={{
                        opacity: 1,
                        x: 0,
                    }}
                    exit={{
                        opacity: 0,
                        x: animationDirection === "right" ? -50 : 50,
                    }}
                    transition={{duration: 0.3}}
                >
                    {/* 이전 달 빈 칸 */}
                    {Array.from({length: getDay(monthStart)}).map((_, index) => (
                        <div key={`empty-start-${index}`} className="h-36 rounded-lg bg-gray-50 p-1 opacity-50"/>
                    ))}

                    {/* 날짜 칸 */}
                    {days.map((day) => {
                        const dayEvents = getEventsForDay(day)
                        const difficultyCounts = getDifficultyCountsForDay(dayEvents)
                        const hasEvents = dayEvents.length > 0
                        const dayOfWeek = getDay(day)
                        const dayColor = dayColors[dayOfWeek]

                        // 해당 날짜의 모든 암장 목록
                        const gymsForDay = Array.from(new Set(dayEvents.map((e) => e.climbingGymName)))

                        // 해당 날짜의 모든 암장에 대한 난이도 시스템 가져오기
                        const difficultySystemsForDay = gymsForDay.map((gym) => getDifficultySystemByGymName("gym"))

                        // 모든 난이도 색상 정보 합치기 (중복 제거)
                        const allDifficultiesForDay = Array.from(
                            new Set(difficultySystemsForDay.flatMap((system) => system.difficulties.map((diff) => diff.name))),
                        )
                            .map((name) => {
                                // 해당 난이도의 색상 정보 찾기
                                for (const system of difficultySystemsForDay) {
                                    const diffInfo = system.difficulties.find((d) => d.name === name)
                                    if (diffInfo) return diffInfo
                                }
                                // 기본 시스템에서 찾기
                                return getDifficultySystemByGymName("").difficulties.find((d) => d.name === name)!
                            })
                            .filter(Boolean)

                        return (
                            <motion.div
                                key={day.toISOString()}
                                className={`h-36 rounded-lg p-1 transition-all duration-200 ${
                                    isToday(day) ? "ring-2 ring-sky-400 ring-offset-2" : "hover:shadow-md hover:shadow-sky-100"
                                } ${
                                    !isSameMonth(day, currentMonth)
                                        ? "bg-gray-50 opacity-50"
                                        : hasEvents
                                            ? "bg-white shadow-sm border border-gray-100"
                                            : "bg-gray-50/80"
                                }`}
                                whileHover={{scale: 1.02}}
                                transition={{type: "spring", stiffness: 300, damping: 20}}
                            >
                                <div className="flex justify-between">
                  <span
                      className={`
                    flex items-center justify-center w-6 h-6 rounded-full text-sm font-medium
                    ${isToday(day) ? "bg-sky-500 text-white" : dayColor}
                  `}
                  >
                    {format(day, "d")}
                  </span>
                                    <Button
                                        variant="ghost"
                                        size="icon"
                                        className="h-6 w-6 rounded-full hover:bg-sky-100 transition-colors text-gray-500 hover:text-sky-600"
                                        onClick={() => handleDateClick(day)}
                                    >
                                        <Plus className="h-3 w-3"/>
                                    </Button>
                                </div>

                                {hasEvents && (
                                    <div className="mt-1 space-y-1">
                                        {/* 이벤트 목록 */}
                                        {dayEvents.slice(0, 2).map((event) => {
                                            const eventStyle = eventTypeStyles["bouldering"]

                                            return (
                                                <TooltipProvider key={event.id}>
                                                    <Tooltip>
                                                        <TooltipTrigger asChild>
                                                            <motion.div
                                                                className={`
                                  cursor-pointer rounded-md px-2 py-1 text-xs font-medium
                                  ${eventStyle.bg} ${eventStyle.text}
                                  border-l-2 ${eventStyle.border}
                                `}
                                                                onClick={() => handleEventClick(event)}
                                                                whileHover={{scale: 1.03}}
                                                                whileTap={{scale: 0.98}}
                                                            >
                                                                <div className="flex items-center">
                                                                    <span className="mr-1">{eventStyle.icon}</span>
                                                                    <span className="truncate">{event.title}</span>
                                                                </div>
                                                            </motion.div>
                                                        </TooltipTrigger>
                                                        <TooltipContent
                                                            className="p-3 max-w-xs bg-white border border-gray-200 shadow-md">
                                                            <div className="space-y-2">
                                                                <div
                                                                    className="font-bold text-base text-sky-700">{event.title}</div>
                                                                <div
                                                                    className="flex items-center text-sm text-gray-700">
                                                                    <MapPin className="h-3 w-3 mr-1 opacity-70"/>
                                                                    {event.climbingGymName}
                                                                </div>
                                                                <div
                                                                    className="flex items-center text-sm text-gray-700">
                                                                    <Clock className="h-3 w-3 mr-1 opacity-70"/>
                                                                    {format(parseISO(event.scheduleDate), "yyyy년 MM월 dd일")}
                                                                </div>
                                                                {event.memo && (
                                                                    <div
                                                                        className="text-sm border-t border-gray-100 pt-1 mt-1 text-gray-600">
                                                                        {event.memo}
                                                                    </div>
                                                                )}
                                                            </div>
                                                        </TooltipContent>
                                                    </Tooltip>
                                                </TooltipProvider>
                                            )
                                        })}

                                        {/* 추가 이벤트 표시 */}
                                        {dayEvents.length > 2 && (
                                            <div className="text-xs text-center font-medium text-sky-600">
                                                + {dayEvents.length - 2}개 더 보기
                                            </div>
                                        )}

                                        {/* 암장 정보 표시 - 길이 제한 */}
                                        <div className="mt-1 text-xs text-gray-600 flex items-center overflow-hidden">
                                            <MapPin className="h-3 w-3 min-w-[12px] mr-1 opacity-70"/>
                                            <span className="truncate">
                        {gymsForDay.length <= 2
                            ? gymsForDay.join(", ")
                            : `${gymsForDay.slice(0, 2).join(", ")} 외 ${gymsForDay.length - 2}곳`}
                      </span>
                                        </div>

                                        {/* 난이도별 클리어 횟수 표시 - 해당 날짜의 암장 난이도 시스템에 맞게 표시 */}
                                        <div className="mt-1 overflow-hidden">
                                            {(() => {
                                                // 카운트가 있는 난이도만 필터링
                                                const visibleDifficulties = Object.keys(difficultyCounts).map(key => {
                                                    const count = difficultyCounts[key]
                                                    return {name: Colors[key], count}
                                                })
                                                // 최대 4개까지만 표시
                                                const displayLimit = 4
                                                const displayDifficulties = visibleDifficulties.slice(0, displayLimit)
                                                const remainingCount = visibleDifficulties.length - displayLimit

                                                return (
                                                    <div className="flex flex-wrap gap-1">
                                                        {displayDifficulties.map((color) => {
                                                            const count = color.count
                                                            return (
                                                                <div
                                                                    key={color.name.key}
                                                                    className={`rounded-full px-1.5 py-0.5 text-xs font-medium ${color.name.bgColor} ${color.name.textColor} border ${color.name.borderColor}`}
                                                                >
                                                                    {color.name.displayName.charAt(0)}
                                                                    {count}
                                                                </div>
                                                            )
                                                        })}

                                                        {remainingCount > 0 && (
                                                            <div
                                                                className="rounded-full px-1.5 py-0.5 text-xs font-medium bg-gray-100 text-gray-700 border border-gray-200">
                                                                +{remainingCount}
                                                            </div>
                                                        )}
                                                    </div>
                                                )
                                            })()}
                                        </div>
                                    </div>
                                )}
                            </motion.div>
                        )
                    })}

                    {/* 다음 달 빈 칸 */}
                    {Array.from({length: 6 - getDay(monthEnd)}).map((_, index) => (
                        <div key={`empty-end-${index}`} className="h-36 rounded-lg bg-gray-50 p-1 opacity-50"/>
                    ))}
                </motion.div>
            </AnimatePresence>

            {/* 이벤트 다이얼로그 */}
            <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
                <DialogContent className="sm:max-w-[425px] bg-white border-gray-200">
                    <DialogHeader>
                        <DialogTitle className="text-sky-700">{selectedEvent ? "세션 수정" : "새 클라이밍 세션 추가"}</DialogTitle>
                    </DialogHeader>
                    <EventForm
                        selectedDate={selectedDate}
                        selectedEvent={selectedEvent}
                        onSubmit={(eventData) => {
                            if (selectedEvent) {
                                updateEvent(selectedEvent.id, eventData)
                            } else {
                                addEvent(eventData)
                            }
                            setIsDialogOpen(false)
                        }}
                        onDelete={() => {
                            if (selectedEvent) {
                                deleteEvent(selectedEvent.id)
                                setIsDialogOpen(false)
                            }
                        }}
                    />
                </DialogContent>
            </Dialog>
        </div>
    )
}

