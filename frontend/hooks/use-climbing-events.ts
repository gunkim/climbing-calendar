"use client"

import {useEffect, useState} from "react";
import {
  createSchedule,
  CreateScheduleRequest,
  deleteSchedule,
  fetchSchedules,
  GetScheduleResponse,
  updateSchedule,
} from "@/apis/climbing-gym";

export function useClimbingEvents() {
  const [events, setEvents] = useState<GetScheduleResponse[]>([]);

  // 초기 데이터 fetch
  useEffect(() => {
    fetchAndSetEvents();
  }, []);

  // 이벤트 데이터를 불러오는 함수
  const fetchAndSetEvents = async () => {
    const response = await fetchSchedules();
    setEvents(response);
  };

  const addEvent = async (eventData: Omit<CreateScheduleRequest, "id">) => {
    await createSchedule(eventData); // 새로운 일정 생성
    await fetchAndSetEvents(); // 이후 데이터 다시 fetch
  };

  const updateEvent = async (
      id: number,
      updatedEvent: Omit<CreateScheduleRequest, "id">
  ) => {
    await updateSchedule(id, updatedEvent); // 일정 업데이트
    await fetchAndSetEvents(); // 이후 데이터 다시 fetch
  };

  const deleteEvent = async (id: number) => {
    await deleteSchedule(id); // 일정 삭제
    await fetchAndSetEvents(); // 이후 데이터 다시 fetch
  };

  return {
    events,
    addEvent,
    updateEvent,
    deleteEvent,
  };
}