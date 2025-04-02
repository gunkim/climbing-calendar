export interface ClimbingEvent {
  id: string
  title: string
  date: string
  type: "bouldering" | "lead" | "training"
  location: string
  notes: string
  difficulties: {
    // 기본 난이도
    red?: number
    orange?: number
    yellow?: number
    green?: number
    blue?: number
    purple?: number
    black?: number

    // 추가 난이도
    pink?: number
    white?: number
    gray?: number

    // 기타 가능한 난이도들
    [key: string]: number | undefined
  }
}

