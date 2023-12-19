<script>
    import {page} from '$app/stores';
    // 페이지 매개변수로부터 boardCode 추출
    const boardCode = $page.params['boardCode'];
    // 각 보드 코드에 대한 이름 매핑
    const boardNames = {
        notice: '공지사항',
        free1: '잡담',
        free2: '방명록'
    };
    // 매핑된 이름 가져오기, 매핑되지 않았다면 기본값으로 '게시판' 사용
    const boardName = ((boardNames[boardCode] || '') + ' 게시판').trim();
</script>

<h1>{boardName}</h1>