// AJAX API
function ajaxAPI(url, jsonData, method) {
    return new Promise(function(resolve, reject) {
        // Ajax 요청을 위한 XMLHttpRequest 객체 생성
        const xhr = new XMLHttpRequest();

        xhr.onload = function() {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                resolve(xhr.response);
            } else {
                reject({ status: xhr.status, statusText: xhr.statusText });
            }
        }

        xhr.open(method, contextPath + url);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader(header, token);
        xhr.responseType = "json";

        if(method == "get" || method == "GET")
            xhr.send();
        else
            xhr.send(JSON.stringify(jsonData)); //post body json 방식 일때
    });
};





// AJAX FILE API
function ajaxFile(url, formData, method) {
    return new Promise(function(resolve, reject) {
        // Ajax 요청을 위한 XMLHttpRequest 객체 생성
        const xhr = new XMLHttpRequest();

        xhr.onload = function() {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                resolve(xhr.response);
            } else {
                reject({ status: xhr.status, statusText: xhr.statusText });
            }
        }

        xhr.open(method, contextPath + url);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader(header, token);

        // 파일 업로드 시에는 필요한 헤더 설정
        // Content-Type은 설정하지 않습니다. 브라우저가 자동으로 설정합니다.

        // FormData를 전송
        xhr.send(formData);
    });
}