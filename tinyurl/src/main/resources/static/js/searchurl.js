document.getElementById("go-button").addEventListener("click", function(event) {
    event.preventDefault();  // 링크의 기본 동작(페이지 이동)을 방지합니다.

    const longUrl = document.getElementById("long-url").value;  // 사용자가 입력한 긴 URL

    // 입력된 URL이 비어 있는지 확인
    if (!longUrl) {
        alert("Please enter a URL.");
        return;
    }

    // API 요청을 위한 URL과 헤더 설정
    fetch('/api/shorten', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ url: longUrl })  // JSON 형식으로 URL을 전송
    })
    .then(response => response.json())  // 응답을 JSON으로 변환
    .then(data => {
        if (data.shortenedUrl) {
            // 단축된 URL이 있으면 표시
            document.getElementById("short-url-link").href = data.shortenedUrl;
            document.getElementById("short-url-link").textContent = data.shortenedUrl;
            document.getElementById("shortened-url").style.display = "block";
            document.getElementById("error-message").style.display = "none";  // 에러 메시지 숨기기
        } else {
            // 단축된 URL이 없으면 에러 메시지 표시
            document.getElementById("error-text").textContent = data.message || "An unknown error occurred.";
            document.getElementById("error-message").style.display = "block";
            document.getElementById("shortened-url").style.display = "none";  // 단축된 URL 숨기기
        }
    })
    .catch(error => {
        // API 호출 중 에러가 발생하면 에러 메시지 표시
        document.getElementById("error-text").textContent = "Failed to shorten URL. Please try again later.";
        document.getElementById("error-message").style.display = "block";
        document.getElementById("shortened-url").style.display = "none";  // 단축된 URL 숨기기
        console.error("Error:", error);  // 콘솔에 에러 로그
    });
});
