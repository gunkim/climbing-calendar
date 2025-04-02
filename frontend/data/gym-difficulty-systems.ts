// 암장별 난이도 시스템 정의
export interface DifficultyColor {
  name: string
  label: string
  bgColor: string
  textColor: string
  borderColor: string
}

export interface GymDifficultySystem {
  id: string
  name: string
  difficulties: DifficultyColor[]
}

// 기본 난이도 시스템 (빨주노초파보검)
const defaultSystem: DifficultyColor[] = [
  { name: "red", label: "빨강", bgColor: "bg-red-100", textColor: "text-red-700", borderColor: "border-red-200" },
  {
    name: "orange",
    label: "주황",
    bgColor: "bg-orange-100",
    textColor: "text-orange-700",
    borderColor: "border-orange-200",
  },
  {
    name: "yellow",
    label: "노랑",
    bgColor: "bg-yellow-100",
    textColor: "text-yellow-700",
    borderColor: "border-yellow-200",
  },
  {
    name: "green",
    label: "초록",
    bgColor: "bg-green-100",
    textColor: "text-green-700",
    borderColor: "border-green-200",
  },
  { name: "blue", label: "파랑", bgColor: "bg-blue-100", textColor: "text-blue-700", borderColor: "border-blue-200" },
  {
    name: "purple",
    label: "보라",
    bgColor: "bg-purple-100",
    textColor: "text-purple-700",
    borderColor: "border-purple-200",
  },
  { name: "black", label: "검정", bgColor: "bg-gray-200", textColor: "text-gray-700", borderColor: "border-gray-300" },
]

// 더클라임 난이도 시스템 (핑크, 퍼플, 그린, 블루, 오렌지, 레드, 화이트, 블랙)
const theClimbSystem: DifficultyColor[] = [
  { name: "pink", label: "핑크", bgColor: "bg-pink-100", textColor: "text-pink-700", borderColor: "border-pink-200" },
  {
    name: "purple",
    label: "퍼플",
    bgColor: "bg-purple-100",
    textColor: "text-purple-700",
    borderColor: "border-purple-200",
  },
  {
    name: "green",
    label: "그린",
    bgColor: "bg-green-100",
    textColor: "text-green-700",
    borderColor: "border-green-200",
  },
  { name: "blue", label: "블루", bgColor: "bg-blue-100", textColor: "text-blue-700", borderColor: "border-blue-200" },
  {
    name: "orange",
    label: "오렌지",
    bgColor: "bg-orange-100",
    textColor: "text-orange-700",
    borderColor: "border-orange-200",
  },
  { name: "red", label: "레드", bgColor: "bg-red-100", textColor: "text-red-700", borderColor: "border-red-200" },
  { name: "white", label: "화이트", bgColor: "bg-gray-50", textColor: "text-gray-700", borderColor: "border-gray-200" },
  { name: "black", label: "블랙", bgColor: "bg-gray-200", textColor: "text-gray-700", borderColor: "border-gray-300" },
]

// 클라이밍파크 난이도 시스템 (옐로우, 그린, 블루, 레드, 퍼플, 블랙)
const climbingParkSystem: DifficultyColor[] = [
  {
    name: "yellow",
    label: "옐로우",
    bgColor: "bg-yellow-100",
    textColor: "text-yellow-700",
    borderColor: "border-yellow-200",
  },
  {
    name: "green",
    label: "그린",
    bgColor: "bg-green-100",
    textColor: "text-green-700",
    borderColor: "border-green-200",
  },
  { name: "blue", label: "블루", bgColor: "bg-blue-100", textColor: "text-blue-700", borderColor: "border-blue-200" },
  { name: "red", label: "레드", bgColor: "bg-red-100", textColor: "text-red-700", borderColor: "border-red-200" },
  {
    name: "purple",
    label: "퍼플",
    bgColor: "bg-purple-100",
    textColor: "text-purple-700",
    borderColor: "border-purple-200",
  },
  { name: "black", label: "블랙", bgColor: "bg-gray-200", textColor: "text-gray-700", borderColor: "border-gray-300" },
]

// 더폴 난이도 시스템 (그레이, 그린, 블루, 옐로우, 레드, 블랙)
const theWallSystem: DifficultyColor[] = [
  { name: "gray", label: "그레이", bgColor: "bg-gray-100", textColor: "text-gray-700", borderColor: "border-gray-200" },
  {
    name: "green",
    label: "그린",
    bgColor: "bg-green-100",
    textColor: "text-green-700",
    borderColor: "border-green-200",
  },
  { name: "blue", label: "블루", bgColor: "bg-blue-100", textColor: "text-blue-700", borderColor: "border-blue-200" },
  {
    name: "yellow",
    label: "옐로우",
    bgColor: "bg-yellow-100",
    textColor: "text-yellow-700",
    borderColor: "border-yellow-200",
  },
  { name: "red", label: "레드", bgColor: "bg-red-100", textColor: "text-red-700", borderColor: "border-red-200" },
  { name: "black", label: "블랙", bgColor: "bg-gray-200", textColor: "text-gray-700", borderColor: "border-gray-300" },
]

// 버티컬월드 난이도 시스템 (화이트, 그린, 블루, 레드, 블랙)
const verticalWorldSystem: DifficultyColor[] = [
  { name: "white", label: "화이트", bgColor: "bg-gray-50", textColor: "text-gray-700", borderColor: "border-gray-200" },
  {
    name: "green",
    label: "그린",
    bgColor: "bg-green-100",
    textColor: "text-green-700",
    borderColor: "border-green-200",
  },
  { name: "blue", label: "블루", bgColor: "bg-blue-100", textColor: "text-blue-700", borderColor: "border-blue-200" },
  { name: "red", label: "레드", bgColor: "bg-red-100", textColor: "text-red-700", borderColor: "border-red-200" },
  { name: "black", label: "블랙", bgColor: "bg-gray-200", textColor: "text-gray-700", borderColor: "border-gray-300" },
]

// 클라임온 난이도 시스템 (그린, 블루, 퍼플, 오렌지, 레드, 블랙)
const climbOnSystem: DifficultyColor[] = [
  {
    name: "green",
    label: "그린",
    bgColor: "bg-green-100",
    textColor: "text-green-700",
    borderColor: "border-green-200",
  },
  { name: "blue", label: "블루", bgColor: "bg-blue-100", textColor: "text-blue-700", borderColor: "border-blue-200" },
  {
    name: "purple",
    label: "퍼플",
    bgColor: "bg-purple-100",
    textColor: "text-purple-700",
    borderColor: "border-purple-200",
  },
  {
    name: "orange",
    label: "오렌지",
    bgColor: "bg-orange-100",
    textColor: "text-orange-700",
    borderColor: "border-orange-200",
  },
  { name: "red", label: "레드", bgColor: "bg-red-100", textColor: "text-red-700", borderColor: "border-red-200" },
  { name: "black", label: "블랙", bgColor: "bg-gray-200", textColor: "text-gray-700", borderColor: "border-gray-300" },
]

// 더락 난이도 시스템 (옐로우, 그린, 블루, 레드, 퍼플, 블랙)
const theRockSystem: DifficultyColor[] = [
  {
    name: "yellow",
    label: "옐로우",
    bgColor: "bg-yellow-100",
    textColor: "text-yellow-700",
    borderColor: "border-yellow-200",
  },
  {
    name: "green",
    label: "그린",
    bgColor: "bg-green-100",
    textColor: "text-green-700",
    borderColor: "border-green-200",
  },
  { name: "blue", label: "블루", bgColor: "bg-blue-100", textColor: "text-blue-700", borderColor: "border-blue-200" },
  { name: "red", label: "레드", bgColor: "bg-red-100", textColor: "text-red-700", borderColor: "border-red-200" },
  {
    name: "purple",
    label: "퍼플",
    bgColor: "bg-purple-100",
    textColor: "text-purple-700",
    borderColor: "border-purple-200",
  },
  { name: "black", label: "블랙", bgColor: "bg-gray-200", textColor: "text-gray-700", borderColor: "border-gray-300" },
]

// 암장별 난이도 시스템 매핑
export const gymDifficultySystems: GymDifficultySystem[] = [
  {
    id: "default",
    name: "기본 시스템",
    difficulties: defaultSystem,
  },
  {
    id: "theclimb",
    name: "더클라임",
    difficulties: theClimbSystem,
  },
  {
    id: "climbingpark",
    name: "클라이밍파크",
    difficulties: climbingParkSystem,
  },
  {
    id: "thewall",
    name: "더폴",
    difficulties: theWallSystem,
  },
  {
    id: "verticalworld",
    name: "버티컬월드",
    difficulties: verticalWorldSystem,
  },
  {
    id: "climbon",
    name: "클라임온",
    difficulties: climbOnSystem,
  },
  {
    id: "therock",
    name: "더락",
    difficulties: theRockSystem,
  },
]

// 암장 이름으로 난이도 시스템 찾기
export function getDifficultySystemByGymName(gymName: string): GymDifficultySystem {
  // 암장 이름에 특정 키워드가 포함되어 있는지 확인
  if (gymName.includes("더클라임")) {
    return gymDifficultySystems.find((system) => system.id === "theclimb") || gymDifficultySystems[0]
  } else if (gymName.includes("클라이밍파크")) {
    return gymDifficultySystems.find((system) => system.id === "climbingpark") || gymDifficultySystems[0]
  } else if (gymName.includes("더폴")) {
    return gymDifficultySystems.find((system) => system.id === "thewall") || gymDifficultySystems[0]
  } else if (gymName.includes("버티컬월드")) {
    return gymDifficultySystems.find((system) => system.id === "verticalworld") || gymDifficultySystems[0]
  } else if (gymName.includes("클라임온")) {
    return gymDifficultySystems.find((system) => system.id === "climbon") || gymDifficultySystems[0]
  } else if (gymName.includes("더락")) {
    return gymDifficultySystems.find((system) => system.id === "therock") || gymDifficultySystems[0]
  }

  // 일치하는 암장이 없으면 기본 시스템 반환
  return gymDifficultySystems[0]
}

// 모든 가능한 난이도 이름 목록 (모든 시스템의 난이도를 합친 것)
export const allDifficultyNames = Array.from(
  new Set(gymDifficultySystems.flatMap((system) => system.difficulties.map((diff) => diff.name))),
)

