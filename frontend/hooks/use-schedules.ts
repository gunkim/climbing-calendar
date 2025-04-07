"use client"

import {useEffect, useState} from "react";
import {
    createScheduleApi,
    CreateScheduleRequest,
    deleteScheduleApi,
    fetchSchedulesApi,
    GetScheduleResponse,
    updateScheduleApi
} from "@/apis/schedules";

export function useSchedules(date: Date) {
    const [schedules, setSchedules] = useState<GetScheduleResponse[]>([]);

    useEffect(() => {
        (async () => {
            await fetchAndSetSchedules(date);
        })()
    }, [date]);

    const fetchAndSetSchedules = async (date: Date) => {
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const response = await fetchSchedulesApi(year, month);
        setSchedules(response);
    };

    const addSchedule = async (eventData: Omit<CreateScheduleRequest, "id">) => {
        await createScheduleApi(eventData); // 새로운 일정 생성
        await fetchAndSetSchedules(date); // 이후 데이터 다시 fetch
    };

    const updateSchedule = async (
        id: number,
        updatedEvent: Omit<CreateScheduleRequest, "id">
    ) => {
        await updateScheduleApi(id, updatedEvent);
        await fetchAndSetSchedules(date); // 이후 데이터 다시 fetch
    };

    const deleteSchedule = async (id: number) => {
        await deleteScheduleApi(id); // 일정 삭제
        await fetchAndSetSchedules(date); // 이후 데이터 다시 fetch
    };

    return {
        schedules: schedules,
        addSchedule: addSchedule,
        updateSchedule: updateSchedule,
        deleteSchedule: deleteSchedule,
    };
}