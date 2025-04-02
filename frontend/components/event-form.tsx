"use client"

import { format } from "date-fns"
import { useState, useEffect } from "react"
import { Button } from "@/components/ui/button"
import { Calendar } from "@/components/ui/calendar"
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"
import { Textarea } from "@/components/ui/textarea"
import { CalendarIcon, Trash2, Plus, Minus, MountainSnow, Route, Dumbbell } from "lucide-react"
import type { ClimbingEvent } from "@/types/climbing-event"
import { useForm } from "react-hook-form"
import { cn } from "@/lib/utils"
import { GymCombobox } from "./gym-combobox"
import { motion } from "framer-motion"
import { getDifficultySystemByGymName, type DifficultyColor, allDifficultyNames } from "@/data/gym-difficulty-systems"

interface EventFormProps {
  selectedDate: Date | null
  selectedEvent: ClimbingEvent | null
  onSubmit: (data: Omit<ClimbingEvent, "id">) => void
  onDelete?: () => void
}

export function EventForm({ selectedDate, selectedEvent, onSubmit, onDelete }: EventFormProps) {
  // 선택된 암장의 난이도 시스템
  const [difficultySystem, setDifficultySystem] = useState<DifficultyColor[]>([])

  // 기본값 설정 - 모든 가능한 난이도에 대해 0으로 초기화
  const defaultDifficulties: Record<string, number> = {}
  allDifficultyNames.forEach((name) => {
    defaultDifficulties[name] = 0
  })

  // 선택된 이벤트가 있으면 해당 값으로 초기화
  if (selectedEvent?.difficulties) {
    Object.keys(selectedEvent.difficulties).forEach((key) => {
      if (selectedEvent.difficulties[key] !== undefined) {
        defaultDifficulties[key] = selectedEvent.difficulties[key] || 0
      }
    })
  }

  const form = useForm({
    defaultValues: {
      title: selectedEvent?.title || "",
      type: selectedEvent?.type || "bouldering",
      location: selectedEvent?.location || "",
      notes: selectedEvent?.notes || "",
      date: selectedEvent?.date || (selectedDate ? selectedDate.toISOString() : undefined),
      difficulties: defaultDifficulties,
    },
  })

  // 암장 선택에 따른 난이도 시스템 변경
  useEffect(() => {
    const location = form.watch("location")
    if (location) {
      const system = getDifficultySystemByGymName(location)
      setDifficultySystem(system.difficulties)
    } else {
      // 암장이 선택되지 않았을 때는 기본 시스템 사용
      const defaultSystem = getDifficultySystemByGymName("")
      setDifficultySystem(defaultSystem.difficulties)
    }
  }, [form.watch("location")])

  const handleSubmit = (data: any) => {
    if (!data.date) return

    // 현재 선택된 난이도 시스템에 있는 난이도만 제출
    const filteredDifficulties: Record<string, number> = {}
    difficultySystem.forEach((diff) => {
      filteredDifficulties[diff.name] = data.difficulties[diff.name] || 0
    })

    onSubmit({
      title: data.title,
      type: data.type,
      location: data.location,
      notes: data.notes,
      date: data.date,
      difficulties: filteredDifficulties,
    })
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(handleSubmit)} className="space-y-5">
        <FormField
          control={form.control}
          name="title"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="text-gray-700">제목</FormLabel>
              <FormControl>
                <Input placeholder="클라이밍 세션 제목" {...field} className="border-gray-200" />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="type"
          render={({ field }) => (
            <FormItem className="space-y-3">
              <FormLabel className="text-gray-700">클라이밍 유형</FormLabel>
              <FormControl>
                <RadioGroup
                  onValueChange={field.onChange}
                  defaultValue={field.value}
                  className="flex flex-col space-y-1 sm:flex-row sm:space-y-0 sm:space-x-2"
                >
                  <FormItem className="flex items-center space-x-2 space-y-0">
                    <FormControl>
                      <RadioGroupItem value="bouldering" id="bouldering" className="peer sr-only" />
                    </FormControl>
                    <label
                      htmlFor="bouldering"
                      className="flex items-center justify-center gap-2 rounded-md border-2 border-gray-200 bg-white px-3 py-2 text-sm font-medium ring-offset-background hover:bg-gray-50 peer-data-[state=checked]:border-sky-500 peer-data-[state=checked]:bg-sky-50 peer-data-[state=checked]:text-sky-700 cursor-pointer"
                    >
                      <MountainSnow className="h-4 w-4" />
                      볼더링
                    </label>
                  </FormItem>

                  <FormItem className="flex items-center space-x-2 space-y-0">
                    <FormControl>
                      <RadioGroupItem value="lead" id="lead" className="peer sr-only" />
                    </FormControl>
                    <label
                      htmlFor="lead"
                      className="flex items-center justify-center gap-2 rounded-md border-2 border-gray-200 bg-white px-3 py-2 text-sm font-medium ring-offset-background hover:bg-gray-50 peer-data-[state=checked]:border-sky-500 peer-data-[state=checked]:bg-sky-50 peer-data-[state=checked]:text-sky-700 cursor-pointer"
                    >
                      <Route className="h-4 w-4" />
                      리드
                    </label>
                  </FormItem>

                  <FormItem className="flex items-center space-x-2 space-y-0">
                    <FormControl>
                      <RadioGroupItem value="training" id="training" className="peer sr-only" />
                    </FormControl>
                    <label
                      htmlFor="training"
                      className="flex items-center justify-center gap-2 rounded-md border-2 border-gray-200 bg-white px-3 py-2 text-sm font-medium ring-offset-background hover:bg-gray-50 peer-data-[state=checked]:border-sky-500 peer-data-[state=checked]:bg-sky-50 peer-data-[state=checked]:text-sky-700 cursor-pointer"
                    >
                      <Dumbbell className="h-4 w-4" />
                      훈련
                    </label>
                  </FormItem>
                </RadioGroup>
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="date"
          render={({ field }) => (
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
                      <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
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
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="location"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="text-gray-700">암장</FormLabel>
              <FormControl>
                <GymCombobox
                  value={field.value}
                  onChange={(value) => {
                    field.onChange(value)
                  }}
                  gymColor="sky"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        {/* 난이도 카운터 UI - 선택된 암장의 난이도 시스템에 맞게 표시 */}
        <div className="space-y-3">
          <h3 className="text-sm font-medium text-gray-700">난이도별 클리어 횟수</h3>
          <div className="grid grid-cols-1 gap-3 sm:grid-cols-2">
            {difficultySystem.map((color) => (
              <motion.div
                key={color.name}
                className={`flex items-center rounded-lg p-2 ${color.bgColor} ${color.textColor} shadow-sm border ${color.borderColor}`}
                whileHover={{ scale: 1.02 }}
                whileTap={{ scale: 0.98 }}
              >
                <span className="mr-2 w-10 text-sm font-medium">{color.label}</span>
                <div className="flex items-center ml-auto">
                  <Button
                    type="button"
                    variant="outline"
                    size="icon"
                    className={`h-7 w-7 bg-white/70 hover:bg-white border-gray-200`}
                    onClick={() => {
                      const currentValue = form.getValues(`difficulties.${color.name}`) || 0
                      if (currentValue > 0) {
                        form.setValue(`difficulties.${color.name}`, currentValue - 1)
                      }
                    }}
                  >
                    <Minus className="h-3 w-3" />
                  </Button>
                  <div className="mx-1 h-7 w-10 flex items-center justify-center bg-white/70 rounded-md font-medium">
                    {form.watch(`difficulties.${color.name}`) || 0}
                  </div>
                  <Button
                    type="button"
                    variant="outline"
                    size="icon"
                    className={`h-7 w-7 bg-white/70 hover:bg-white border-gray-200`}
                    onClick={() => {
                      const currentValue = form.getValues(`difficulties.${color.name}`) || 0
                      form.setValue(`difficulties.${color.name}`, currentValue + 1)
                    }}
                  >
                    <Plus className="h-3 w-3" />
                  </Button>
                </div>
              </motion.div>
            ))}
          </div>
        </div>

        <FormField
          control={form.control}
          name="notes"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="text-gray-700">메모</FormLabel>
              <FormControl>
                <Textarea
                  placeholder="완등한 문제, 프로젝트 등 메모"
                  className="resize-none border-gray-200"
                  {...field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <div className="flex justify-between pt-2">
          {selectedEvent && onDelete && (
            <Button type="button" variant="destructive" size="sm" onClick={onDelete} className="gap-1">
              <Trash2 className="h-4 w-4" />
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

