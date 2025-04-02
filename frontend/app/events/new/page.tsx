"use client"

import { useState } from "react"
import { useRouter } from "next/navigation"
import { ClimbingHeader } from "@/components/climbing-header"
import { EventForm } from "@/components/event-form"
import { useClimbingEvents } from "@/hooks/use-climbing-events"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"

export default function NewEventPage() {
  const router = useRouter()
  const { addEvent } = useClimbingEvents()
  const [selectedDate] = useState<Date>(new Date())

  return (
    <div className="min-h-screen bg-background">
      <ClimbingHeader />
      <main className="container mx-auto py-6 px-4">
        <Card className="mx-auto max-w-md">
          <CardHeader>
            <CardTitle>새 클라이밍 세션 추가</CardTitle>
          </CardHeader>
          <CardContent>
            <EventForm
              selectedDate={selectedDate}
              selectedEvent={null}
              onSubmit={(eventData) => {
                addEvent(eventData)
                router.push("/")
              }}
            />
          </CardContent>
        </Card>
      </main>
    </div>
  )
}

