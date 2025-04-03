"use client"

import {useEffect, useState} from "react"
import type {ClimbingEvent} from "@/types/climbing-event"
import {createSchedule, CreateScheduleRequest, fetchSchedules, GetScheduleResponse} from "@/apis/climbing-gym";

export function useClimbingEvents() {
    const [events, setEvents] = useState<GetScheduleResponse[]>([])

    useEffect(() => {
        (async () => {
            await fetchSchedules()
        })()
    }, [])

    const addEvent = (eventData: Omit<CreateScheduleRequest, "id">) => {
        (async () => {
            return await createSchedule(eventData)
        })()
    }

    const updateEvent = (updatedEvent: ClimbingEvent) => {
    }

    const deleteEvent = (eventId: string) => {
    }

    return {
        events,
        addEvent,
        updateEvent,
        deleteEvent,
    }
}

