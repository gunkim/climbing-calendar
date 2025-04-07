package github.gunkim.climbingcalendar.common;

public record Pageable(int page, int size, String sortBy) {
    /**
     * Pageable 생성자에서 페이지와 크기에 대한 유효성 검사를 수행합니다.
     *
     * @param page   the page number, 0 이상이어야 함.
     * @param size   the page 크기, 0보다 커야 함.
     * @param sortBy 정렬 기준, null 허용.
     * @throws IllegalArgumentException 페이지가 음수이거나 크기가 0 이하인 경우.
     */
    public Pageable {
        validatePaginationParameters(page, size);
    }

    /**
     * 주어진 페이지와 크기 값이 null인 경우 기본값(0, 10)을 사용하여 Pageable 객체를 생성합니다.
     *
     * @param page nullable page, null인 경우 0으로 대체.
     * @param size nullable size, null인 경우 10으로 대체.
     * @return 생성된 Pageable 객체.
     */
    public static Pageable of(Integer page, Integer size) {
        return Pageable.of(page, size, null);
    }

    /**
     * 페이지, 크기, 및 정렬 기준(sortBy)을 사용하여 Pageable 객체를 생성합니다.
     *
     * @param page   the page number.
     * @param size   the page 크기.
     * @param sortBy 정렬 기준.
     * @return 생성된 Pageable 객체.
     */
    public static Pageable of(Integer page, Integer size, String sortBy) {
        int validPage = (page != null) ? page : 0;
        int validSize = (size != null) ? size : 10;
        return new Pageable(validPage, validSize, sortBy);
    }

    /**
     * 현재 페이지 및 크기를 기반으로 오프셋(시작 인덱스)을 계산합니다.
     *
     * @return 계산된 오프셋.
     */
    public int offset() {
        return this.page * this.size;
    }

    /**
     * 페이지와 크기 값의 유효성을 검사하는 헬퍼 메서드입니다.
     *
     * @param page the page number.
     * @param size the page 크기.
     */
    private static void validatePaginationParameters(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("페이지는 0 이상이어야 합니다.");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("크기는 0보다 커야 합니다.");
        }
    }
}