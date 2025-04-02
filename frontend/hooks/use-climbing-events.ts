"use client"

import { useState, useEffect } from "react"
import type { ClimbingEvent } from "@/types/climbing-event"
import { v4 as uuidv4 } from "uuid"
import { allDifficultyNames } from "@/data/gym-difficulty-systems"

export function useClimbingEvents() {
  const [events, setEvents] = useState<ClimbingEvent[]>([])

  // Load events from localStorage on initial render
  useEffect(() => {
    const storedEvents = localStorage.getItem("climbingEvents")
    if (storedEvents) {
      try {
        const parsedEvents = JSON.parse(storedEvents)

        // 기존 이벤트에 난이도 정보가 없는 경우 기본값 추가
        const updatedEvents = parsedEvents.map((event: ClimbingEvent) => {
          if (!event.difficulties) {
            const defaultDifficulties: Record<string, number> = {}
            allDifficultyNames.forEach((name) => {
              defaultDifficulties[name] = 0
            })

            return {
              ...event,
              difficulties: defaultDifficulties,
            }
          }
          return event
        })

        setEvents(updatedEvents)
      } catch (error) {
        console.error("Failed to parse stored events:", error)
      }
    }
  }, [])

  // Save events to localStorage whenever they change
  useEffect(() => {
    localStorage.setItem("climbingEvents", JSON.stringify(events))
  }, [events])

  const addEvent = (eventData: Omit<ClimbingEvent, "id">) => {
    const newEvent: ClimbingEvent = {
      ...eventData,
      id: uuidv4(),
    }
    setEvents((prevEvents) => [...prevEvents, newEvent])
  }

  const updateEvent = (updatedEvent: ClimbingEvent) => {
    setEvents((prevEvents) => prevEvents.map((event) => (event.id === updatedEvent.id ? updatedEvent : event)))
  }

  const deleteEvent = (eventId: string) => {
    setEvents((prevEvents) => prevEvents.filter((event) => event.id !== eventId))
  }

  return {
    events,
    addEvent,
    updateEvent,
    deleteEvent,
  }
}

