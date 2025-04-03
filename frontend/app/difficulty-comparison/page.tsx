// "use client"
//
// import { useState } from "react"
// import { ClimbingHeader } from "@/components/climbing-header"
// import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card"
// import { Button } from "@/components/ui/button"
// import { ScrollArea } from "@/components/ui/scroll-area"
// import { gymDifficultySystems } from "@/data/gym-difficulty-systems"
// import { Info } from "lucide-react"
// import { MultiGymSelector } from "@/components/multi-gym-selector"
// import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from "@/components/ui/tooltip"
//
// // V-스케일 난이도 정의
// const vScales = [
//   { scale: "V0", description: "초보자 난이도" },
//   { scale: "V1", description: "초보자-초중급 난이도" },
//   { scale: "V2", description: "초중급 난이도" },
//   { scale: "V3", description: "중급 난이도" },
//   { scale: "V4", description: "중급-중상급 난이도" },
//   { scale: "V5", description: "중상급 난이도" },
//   { scale: "V6", description: "상급 난이도" },
//   { scale: "V7", description: "상급-최상급 난이도" },
//   { scale: "V8", description: "최상급 난이도" },
//   { scale: "V9", description: "전문가 난이도" },
//   { scale: "V10+", description: "프로 난이도" },
// ]
//
// // 암장별 난이도와 V-스케일 매핑 (예시 데이터)
// const difficultyVScaleMapping = {
//   // 더클라임
//   theclimb: {
//     pink: ["V0"],
//     purple: ["V1", "V2"],
//     green: ["V2", "V3"],
//     blue: ["V3", "V4"],
//     orange: ["V4", "V5", "V6"],
//     red: ["V6", "V7"],
//     white: ["V7", "V8"],
//     black: ["V8", "V9", "V10+"],
//   },
//   // 클라이밍파크
//   climbingpark: {
//     yellow: ["V0", "V1"],
//     green: ["V2", "V3"],
//     blue: ["V3", "V4", "V5"],
//     red: ["V5", "V6", "V7"],
//     purple: ["V7", "V8"],
//     black: ["V8", "V9", "V10+"],
//   },
//   // 더폴
//   thewall: {
//     gray: ["V0", "V1"],
//     green: ["V1", "V2", "V3"],
//     blue: ["V3", "V4", "V5"],
//     yellow: ["V5", "V6"],
//     red: ["V6", "V7", "V8"],
//     black: ["V8", "V9", "V10+"],
//   },
//   // 버티컬월드
//   verticalworld: {
//     white: ["V0", "V1"],
//     green: ["V2", "V3"],
//     blue: ["V3", "V4", "V5"],
//     red: ["V5", "V6", "V7", "V8"],
//     black: ["V8", "V9", "V10+"],
//   },
//   // 클라임온
//   climbon: {
//     green: ["V0", "V1", "V2"],
//     blue: ["V2", "V3", "V4"],
//     purple: ["V4", "V5"],
//     orange: ["V5", "V6", "V7"],
//     red: ["V7", "V8"],
//     black: ["V8", "V9", "V10+"],
//   },
//   // 더락
//   therock: {
//     yellow: ["V0", "V1"],
//     green: ["V1", "V2", "V3"],
//     blue: ["V3", "V4", "V5"],
//     red: ["V5", "V6", "V7"],
//     purple: ["V7", "V8"],
//     black: ["V8", "V9", "V10+"],
//   },
//   // 기본 시스템
//   default: {
//     red: ["V6", "V7", "V8"],
//     orange: ["V5", "V6"],
//     yellow: ["V3", "V4"],
//     green: ["V2", "V3"],
//     blue: ["V1", "V2"],
//     purple: ["V0", "V1"],
//     black: ["V8", "V9", "V10+"],
//   },
// }
//
// export default function DifficultyComparisonPage() {
//   // 선택된 암장 ID 목록
//   const [selectedGyms, setSelectedGyms] = useState(["theclimb", "climbingpark", "thewall"])
//
//   // 암장 추가/제거 핸들러
//   const toggleGym = (gymId: string) => {
//     if (selectedGyms.includes(gymId)) {
//       setSelectedGyms(selectedGyms.filter((id) => id !== gymId))
//     } else {
//       setSelectedGyms([...selectedGyms, gymId])
//     }
//   }
//
//   // 선택된 암장 시스템 가져오기
//   const selectedGymSystems = selectedGyms.map(
//     (id) => gymDifficultySystems.find((sys) => sys.id === id) || gymDifficultySystems[0],
//   )
//
//   // V-스케일에 해당하는 각 암장의 색상 찾기
//   const getColorsForVScale = (vScale: string, gymId: string) => {
//     const gymMapping = difficultyVScaleMapping[gymId as keyof typeof difficultyVScaleMapping] || {}
//
//     return Object.entries(gymMapping)
//       .filter(([_, scales]) => scales.includes(vScale))
//       .map(([colorName]) => colorName)
//   }
//
//   // 특정 암장의 특정 색상에 해당하는 V-스케일 가져오기
//   const getVScalesForColor = (colorName: string, gymId: string) => {
//     const gymMapping = difficultyVScaleMapping[gymId as keyof typeof difficultyVScaleMapping] || {}
//     return gymMapping[colorName as keyof typeof gymMapping] || []
//   }
//
//   // 색상 이름으로 색상 객체 찾기
//   const getColorByName = (colorName: string, gymId: string) => {
//     const gymSystem = gymDifficultySystems.find((sys) => sys.id === gymId) || gymDifficultySystems[0]
//     return gymSystem.difficulties.find((diff) => diff.name === colorName)
//   }
//
//   return (
//     <div className="min-h-screen bg-background">
//       <ClimbingHeader />
//       <main className="container mx-auto py-6 px-4">
//         <div className="max-w-6xl mx-auto">
//           <h1 className="text-3xl font-bold mb-2 text-center">암장별 난이도 비교</h1>
//           <p className="text-muted-foreground text-center mb-8">다양한 클라이밍 암장의 난이도 시스템을 비교해보세요.</p>
//
//           <Card className="mb-8">
//             <CardHeader>
//               <CardTitle className="text-lg">비교할 암장 선택</CardTitle>
//               <CardDescription>비교하고 싶은 암장을 선택하세요. 여러 암장을 동시에 비교할 수 있습니다.</CardDescription>
//             </CardHeader>
//             <CardContent>
//               <MultiGymSelector selectedGyms={selectedGyms} onGymToggle={toggleGym} />
//
//               <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-6">
//                 {selectedGymSystems.map((system) => (
//                   <div key={system.id} className="border rounded-lg p-4">
//                     <h3 className="font-medium mb-2">{system.name} 난이도 시스템</h3>
//                     <div className="flex flex-wrap gap-2">
//                       {system.difficulties.map((diff) => {
//                         const vScalesForColor = getVScalesForColor(diff.name, system.id)
//
//                         return (
//                           <TooltipProvider key={diff.name}>
//                             <Tooltip>
//                               <TooltipTrigger asChild>
//                                 <div
//                                   className={`px-3 py-1 rounded-md ${diff.bgColor} ${diff.textColor} border ${diff.borderColor} cursor-help`}
//                                 >
//                                   {diff.label}
//                                 </div>
//                               </TooltipTrigger>
//                               <TooltipContent>
//                                 <p>대략적인 V-스케일: {vScalesForColor.join(", ")}</p>
//                               </TooltipContent>
//                             </Tooltip>
//                           </TooltipProvider>
//                         )
//                       })}
//                     </div>
//                   </div>
//                 ))}
//               </div>
//             </CardContent>
//           </Card>
//
//           <Card>
//             <CardHeader>
//               <div className="flex items-center justify-between">
//                 <CardTitle className="text-lg">V-스케일 기준 난이도 비교표</CardTitle>
//                 <TooltipProvider>
//                   <Tooltip>
//                     <TooltipTrigger asChild>
//                       <Button variant="ghost" size="icon" className="h-8 w-8">
//                         <Info className="h-4 w-4" />
//                       </Button>
//                     </TooltipTrigger>
//                     <TooltipContent className="max-w-sm">
//                       <p>
//                         V-스케일은 볼더링 문제의 난이도를 나타내는 국제적인 척도입니다. V0이 가장 쉽고 숫자가 올라갈수록
//                         어려워집니다. 이 표는 각 암장의 색상 난이도가 대략적으로 어떤 V-스케일에 해당하는지 보여줍니다.
//                       </p>
//                     </TooltipContent>
//                   </Tooltip>
//                 </TooltipProvider>
//               </div>
//               <CardDescription>
//                 V-스케일을 기준으로 각 암장의 색상 난이도를 비교합니다. 하나의 V-스케일이 여러 색상에 걸치거나, 하나의
//                 색상이 여러 V-스케일에 걸칠 수 있습니다.
//               </CardDescription>
//             </CardHeader>
//             <CardContent>
//               <ScrollArea className="h-[500px]">
//                 <div className="w-full overflow-x-auto">
//                   <table className="w-full border-collapse">
//                     <thead className="sticky top-0 bg-white z-10">
//                       <tr className="bg-muted">
//                         <th className="p-3 text-left min-w-[100px]">V-스케일</th>
//                         <th className="p-3 text-left min-w-[150px]">난이도 설명</th>
//                         {selectedGymSystems.map((system) => (
//                           <th key={system.id} className="p-3 text-left min-w-[180px]">
//                             {system.name}
//                           </th>
//                         ))}
//                       </tr>
//                     </thead>
//                     <tbody>
//                       {vScales.map((vScale, index) => (
//                         <tr key={vScale.scale} className={index % 2 === 0 ? "bg-white" : "bg-muted/30"}>
//                           <td className="p-3 font-medium">{vScale.scale}</td>
//                           <td className="p-3 text-sm text-muted-foreground">{vScale.description}</td>
//
//                           {selectedGymSystems.map((system) => {
//                             const colorsForVScale = getColorsForVScale(vScale.scale, system.id)
//
//                             return (
//                               <td key={system.id} className="p-3">
//                                 <div className="flex flex-wrap gap-1">
//                                   {colorsForVScale.map((colorName) => {
//                                     const color = getColorByName(colorName, system.id)
//                                     if (!color) return null
//
//                                     return (
//                                       <div
//                                         key={colorName}
//                                         className={`px-2 py-1 rounded ${color.bgColor} ${color.textColor} border ${color.borderColor}`}
//                                       >
//                                         {color.label}
//                                       </div>
//                                     )
//                                   })}
//
//                                   {colorsForVScale.length === 0 && (
//                                     <span className="text-muted-foreground text-sm">-</span>
//                                   )}
//                                 </div>
//                               </td>
//                             )
//                           })}
//                         </tr>
//                       ))}
//                     </tbody>
//                   </table>
//                 </div>
//               </ScrollArea>
//
//               <div className="p-4 bg-yellow-50 rounded-lg mt-6">
//                 <h3 className="font-medium mb-2">참고사항</h3>
//                 <p className="text-sm text-muted-foreground">
//                   이 비교표는 대략적인 참고용으로만 사용하세요. 실제 난이도는 암장마다, 세터마다 다를 수 있습니다. 같은
//                   색상이라도 암장에 따라 체감 난이도가 다를 수 있으며, 같은 암장 내에서도 문제별로 차이가 있을 수
//                   있습니다. V-스케일과의 매핑은 일반적인 경향을 나타낸 것으로, 정확한 기준이 아닙니다.
//                 </p>
//               </div>
//             </CardContent>
//           </Card>
//
//           <Card className="mt-8">
//             <CardHeader>
//               <CardTitle className="text-lg">암장별 색상 난이도 비교표</CardTitle>
//               <CardDescription>각 암장의 색상을 기준으로 대략적인 V-스케일 범위를 비교합니다.</CardDescription>
//             </CardHeader>
//             <CardContent>
//               <ScrollArea className="h-[500px]">
//                 <div className="w-full overflow-x-auto">
//                   <table className="w-full border-collapse">
//                     <thead className="sticky top-0 bg-white z-10">
//                       <tr className="bg-muted">
//                         <th className="p-3 text-left min-w-[150px]">암장</th>
//                         <th className="p-3 text-left">색상 난이도 (쉬움 → 어려움)</th>
//                       </tr>
//                     </thead>
//                     <tbody>
//                       {selectedGymSystems.map((system) => (
//                         <tr key={system.id}>
//                           <td className="p-3 font-medium">{system.name}</td>
//                           <td className="p-3">
//                             <div className="flex items-center">
//                               <div className="flex-1 h-12 flex items-center">
//                                 {system.difficulties.map((diff, index) => {
//                                   const vScalesForColor = getVScalesForColor(diff.name, system.id)
//
//                                   return (
//                                     <TooltipProvider key={diff.name}>
//                                       <Tooltip>
//                                         <TooltipTrigger asChild>
//                                           <div
//                                             className={`h-full flex-1 flex items-center justify-center ${diff.bgColor} ${diff.textColor} border-y ${diff.borderColor} ${
//                                               index === 0 ? "rounded-l-md border-l" : ""
//                                             } ${
//                                               index === system.difficulties.length - 1 ? "rounded-r-md border-r" : ""
//                                             }`}
//                                           >
//                                             <div className="text-center">
//                                               <div>{diff.label}</div>
//                                               <div className="text-xs">{vScalesForColor.join(", ")}</div>
//                                             </div>
//                                           </div>
//                                         </TooltipTrigger>
//                                         <TooltipContent>
//                                           <p>
//                                             {diff.label}: {vScalesForColor.join(", ")}
//                                           </p>
//                                         </TooltipContent>
//                                       </Tooltip>
//                                     </TooltipProvider>
//                                   )
//                                 })}
//                               </div>
//                               <div className="ml-4 text-sm text-muted-foreground">쉬움 → 어려움</div>
//                             </div>
//                           </td>
//                         </tr>
//                       ))}
//                     </tbody>
//                   </table>
//                 </div>
//               </ScrollArea>
//             </CardContent>
//           </Card>
//         </div>
//       </main>
//     </div>
//   )
// }
//
