"use client"

import {format} from "date-fns"
import {Button} from "@/components/ui/button"
import {Calendar} from "@/components/ui/calendar"
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form"
import {Input} from "@/components/ui/input"
import {Popover, PopoverContent, PopoverTrigger} from "@/components/ui/popover"
import {RadioGroup, RadioGroupItem} from "@/components/ui/radio-group"
import {Textarea} from "@/components/ui/textarea"
import {CalendarIcon, Minus, MountainSnow, Plus, Trash2} from "lucide-react"
import type {ClimbingEvent} from "@/types/climbing-event"
import {useForm} from "react-hook-form"
import {cn} from "@/lib/utils"
import {GymCombobox} from "./gym-combobox"
import {
    CreateScheduleRequest,
    fetchClimbingGyms,
    GetClimbingGymResponse,
    GetScheduleResponse
} from "@/apis/climbing-gym";
import {useEffect, useState} from "react";
import Colors, {ClimbingGymLevel, fetchClimbingGymLevels} from "@/apis/levels";
import {motion} from "framer-motion"

interface EventFormProps {
    selectedDate: Date | null
    selectedEvent: GetScheduleResponse | null
    onSubmit: (data: Omit<CreateScheduleRequest, "id">) => void
    onDelete?: () => void
}

export function EventForm({selectedDate, selectedEvent, onSubmit, onDelete}: EventFormProps) {
    const [climbingGyms, setClimbingGyms] = useState<GetClimbingGymResponse[]>([])
    const [levels, setLevels] = useState<ClimbingGymLevel[]>([])

    const defaultDifficulties: Record<string, number> = {}

    // 선택된 이벤트가 있으면 해당 값으로 초기화
    // if (selectedEvent?.clearList) {
    //     Object.keys(selectedEvent.difficulties).forEach((key) => {
    //         if (selectedEvent.difficulties[key] !== undefined) {
    //             defaultDifficulties[key] = selectedEvent.difficulties[key] || 0
    //         }
    //     })
    // }

    const form = useForm({
        defaultValues: {
            title: selectedEvent?.title || "",
            gymId: selectedEvent?.climbingGymId,
            notes: selectedEvent?.memo || "",
            date: selectedEvent?.scheduleDate || (selectedDate ? selectedDate.toISOString() : undefined),
            difficulties: defaultDifficulties,
        },
    })

    const handleSubmit = (data: any) => {
        if (!data.date) return

        onSubmit({
            title: data.title,
            climbingGymId: data.gymId,
            memo: data.notes,
            scheduleDate: data.date,
            clearList: Object.entries(data.difficulties).map(([key, value]: [string, any]) => ({
                levelId: Number(key),
                count: Number(value),
            }))
        })
    }

    useEffect(() => {
        const selectedGymId = form.watch("gymId") as number
        if (!selectedGymId) return
        (async () => {
            const response = await fetchClimbingGymLevels(selectedGymId)
            setLevels(response)
        })()
    }, [form.watch("gymId")]);

    useEffect(() => {
        (async () => {
            const climbingGymResponses = await fetchClimbingGyms()
            setClimbingGyms(climbingGymResponses)
        })()
    }, [])

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(handleSubmit)} className="space-y-5">
                <FormField
                    control={form.control}
                    name="title"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel className="text-gray-700">제목</FormLabel>
                            <FormControl>
                                <Input placeholder="클라이밍 세션 제목" {...field} className="border-gray-200"/>
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

                <FormField
                    name="type"
                    render={() => (
                        <FormItem className="space-y-3">
                            <FormLabel className="text-gray-700">클라이밍 유형</FormLabel>
                            <FormControl>
                                <RadioGroup
                                    defaultValue={"bouldering"}
                                    className="flex flex-col space-y-1 sm:flex-row sm:space-y-0 sm:space-x-2"
                                >
                                    <FormItem className="flex items-center space-x-2 space-y-0">
                                        <FormControl>
                                            <RadioGroupItem value="bouldering" id="bouldering"
                                                            className="peer sr-only"/>
                                        </FormControl>
                                        <label
                                            htmlFor="bouldering"
                                            className="flex items-center justify-center gap-2 rounded-md border-2 border-gray-200 bg-white px-3 py-2 text-sm font-medium ring-offset-background hover:bg-gray-50 peer-data-[state=checked]:border-sky-500 peer-data-[state=checked]:bg-sky-50 peer-data-[state=checked]:text-sky-700 cursor-pointer"
                                        >
                                            <MountainSnow className="h-4 w-4"/>
                                            볼더링
                                        </label>
                                    </FormItem>
                                </RadioGroup>
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

                <FormField
                    control={form.control}
                    name="date"
                    render={({field}) => (
                        <FormItem className="flex flex-col">
                            <FormLabel className="text-gray-700">날짜</FormLabel>
                            <Popover>
                                <PopoverTrigger asChild>
                                    <FormControl>
                                        <Button
                                            variant={"outline"}
                                            className={cn(
                                                "w-full pl-3 text-left font-normal border-gray-200",
                                                !field.value && "text-gray-500",
                                            )}
                                        >
                                            {field.value ? format(new Date(field.value), "PPP") : <span>날짜 선택</span>}
                                            <CalendarIcon className="ml-auto h-4 w-4 opacity-50"/>
                                        </Button>
                                    </FormControl>
                                </PopoverTrigger>
                                <PopoverContent className="w-auto p-0" align="start">
                                    <Calendar
                                        mode="single"
                                        selected={field.value ? new Date(field.value) : undefined}
                                        onSelect={(date) => field.onChange(date?.toISOString())}
                                        initialFocus
                                    />
                                </PopoverContent>
                            </Popover>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

                <FormField
                    control={form.control}
                    name="gymId"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel className="text-gray-700">암장</FormLabel>
                            <FormControl>
                                <GymCombobox
                                    gymId={field.value}
                                    climbingGyms={climbingGyms}
                                    onChange={(value) => {
                                        field.onChange(value)
                                    }}
                                />
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />
                {levels.length > 0 && <div className="space-y-3">
                    <h3 className="text-sm font-medium text-gray-700">난이도별 클리어 횟수</h3>
                    <div className="grid grid-cols-1 gap-3 sm:grid-cols-2">
                        {levels.map((level) => {
                            const colorAttributes = Colors[level.color as keyof typeof Colors]
                            return (
                                <motion.div
                                    key={level.id}
                                    className={`flex items-center rounded-lg p-2 ${colorAttributes.bgColor} ${colorAttributes.textColor} shadow-sm border ${colorAttributes.borderColor}`}
                                    whileHover={{scale: 1.02}}
                                    whileTap={{scale: 0.98}}
                                >
                                    <span className="mr-2 w-10 text-sm font-medium">{colorAttributes.displayName}</span>
                                    <div className="flex items-center ml-auto">
                                        <Button
                                            type="button"
                                            variant="outline"
                                            size="icon"
                                            className={`h-7 w-7 bg-white/70 hover:bg-white border-gray-200`}
                                            onClick={() => {
                                                const currentValue = form.getValues(`difficulties.${level.color}`) || 0
                                                if (currentValue > 0) {
                                                    form.setValue(`difficulties.${level.id}`, currentValue - 1)
                                                }
                                            }}
                                        >
                                            <Minus className="h-3 w-3"/>
                                        </Button>
                                        <div
                                            className="mx-1 h-7 w-10 flex items-center justify-center bg-white/70 rounded-md font-medium">
                                            {form.watch(`difficulties.${level.id}`) || 0}
                                        </div>
                                        <Button
                                            type="button"
                                            variant="outline"
                                            size="icon"
                                            className={`h-7 w-7 bg-white/70 hover:bg-white border-gray-200`}
                                            onClick={() => {
                                                const currentValue = form.getValues(`difficulties.${level.id}`) || 0
                                                form.setValue(`difficulties.${level.id}`, currentValue + 1)
                                            }}
                                        >
                                            <Plus className="h-3 w-3"/>
                                        </Button>
                                    </div>
                                </motion.div>
                            )
                        })}
                    </div>
                </div>}
                <FormField
                    control={form.control}
                    name="notes"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel className="text-gray-700">메모</FormLabel>
                            <FormControl>
                                <Textarea
                                    placeholder="완등한 문제, 프로젝트 등 메모"
                                    className="resize-none border-gray-200"
                                    {...field}
                                />
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

                <div className="flex justify-between pt-2">
                    {selectedEvent && onDelete && (
                        <Button type="button" variant="destructive" size="sm" onClick={onDelete} className="gap-1">
                            <Trash2 className="h-4 w-4"/>
                            삭제
                        </Button>
                    )}
                    <Button type="submit" className="ml-auto bg-sky-500 hover:bg-sky-600 text-white">
                        {selectedEvent ? "수정" : "추가"}
                    </Button>
                </div>
            </form>
        </Form>
    )
}

