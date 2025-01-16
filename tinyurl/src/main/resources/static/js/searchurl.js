document.getElementById("go-button").addEventListener("click", function (event) {
    event.preventDefault(); // 기본 동작 방지

    const longUrlInput = document.getElementById("long-url");
    let longUrl = longUrlInput.value.trim(); // 입력값 가져오기 및 공백 제거
    if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
        longUrl = "https://" + longUrl;
    }

    // 입력된 URL이 비어 있는지 확인
    if (!longUrl) {
        alert("Please enter a URL."); // 사용자에게 알림
        return;
    }

    // API 요청 URL
    const apiUrl = '/api/shorten';

    // Fetch API 요청
    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ url: longUrl }) // JSON 데이터 전송
    })
        .then(response => {
            // HTTP 상태 코드 확인
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            handleApiResponse(data); // API 응답 처리
        })
        .catch(error => {
            handleError(error); // 오류 처리
        });
});

// API 응답 처리
function handleApiResponse(data) {
    if (data.shortUrl) {
        // 단축 URL 표시
        const shortUrlLink = document.getElementById("short-url-link");
        shortUrlLink.href = data.shortUrl;
        shortUrlLink.textContent = data.shortUrl;

        const shortUrlDetail = document.getElementById("short-url-detail");
        const baseurl = window.location.origin;
        shortUrlDetail.href = `${baseurl}/api/${data.shortUrl}/info`;
        shortUrlDetail.text = "View Details";
        console.log(baseurl);
        document.getElementById("shortened-url").style.display = "block";
        document.getElementById("error-message").style.display = "none"; // 에러 메시지 숨기기
    } else {
        // 에러 메시지 표시
        displayErrorMessage(data.message || "An unknown error occurred.");
    }
}

// 에러 메시지 처리
function handleError(error) {
    console.error("Error:", error); // 콘솔에 로그 출력
    displayErrorMessage("Failed to shorten URL. Please try again later.");
}

// 에러 메시지 표시
function displayErrorMessage(message) {
    const errorText = document.getElementById("error-text");
    errorText.textContent = message;

    document.getElementById("error-message").style.display = "block";
    document.getElementById("shortened-url").style.display = "none"; // 단축 URL 숨기기
}
