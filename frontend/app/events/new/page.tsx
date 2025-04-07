"use client"

import {useState} from "react"
import {useRouter} from "next/navigation"
import {ClimbingHeader} from "@/components/climbing-header"
import {EventForm} from "@/components/event-form"
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card"
import {createScheduleApi} from "@/apis/schedules";

export default function NewEventPage() {
    const router = useRouter()
    const [selectedDate] = useState<Date>(new Date())

    const handleCreateSchedule = async (eventData: any) => {
        try {
            await createScheduleApi(eventData)
            router.push("/")
        } catch (error) {
            console.error("Error creating schedule:", error)
        }
    }

    return <div className="min-h-screen bg-background">
        <ClimbingHeader/>
        <main className="container mx-auto py-6 px-4">
            <Card className="mx-auto max-w-md">
                <CardHeader>
                    <CardTitle>새 클라이밍 세션 추가</CardTitle>
                </CardHeader>
                <CardContent>
                    <EventForm
                        selectedDate={selectedDate}
                        selectedEvent={null}
                        onSubmit={async (eventData) => {
                            await handleCreateSchedule(eventData)
                        }}
                    />
                </CardContent>
            </Card>
        </main>
    </div>
}

