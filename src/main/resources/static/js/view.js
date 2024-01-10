"use strict";

let no = document.getElementById("boardNo"); // 글 번호
let btnDelete = document.getElementById('del')  // 삭제버튼
let btnFileDownload = document.getElementById('fileDown')  // 파일 다운로드
let commentTxt = document.getElementById("commentTxt");   // 댓글 작성 내용
let btnCoRegister = document.getElementById("btnCoRegister");   // 댓글 등록 버튼


document.addEventListener('DOMContentLoaded', function() {
    addEventListenerCRUDBtn();
});



function addEventListenerCRUDBtn(){
    if(btnDelete!= null) btnDelete.addEventListener('click', fnDelete);
    if(btnFileDownload!= null) btnFileDownload.addEventListener('click', fnFileDownLoad);
    if(btnCoRegister!= null) btnCoRegister.addEventListener('click', fnbtnCoRegister);
}


/* 삭제 버튼 */
function fnDelete(){

    let jsonData = {
        "no" : no.value
    };


    if(confirm("삭제 하시겠습니까?")){

        ajaxAPI("/board/delete", jsonData, "POST").then(response => {
        window.location.href = '/';

        });

    }
}

/* 파일 다운로드 */
function fnFileDownLoad() {

    let fileNo = btnFileDownload.getAttribute("data-fno");

    ajaxAPI("/board/fileDownload?fno=" + fileNo, null, "GET").then(response => {
    window.location.href = '/board/fileDownload?fno=' + fileNo;
    })

}



/* 댓글 등록 버튼*/
function fnbtnCoRegister() {

    if(commentTxt.value === ""){
        alert("댓글을 입력해주세요.");
        return;
    }

    let jsonData = {
       "no" : no.value,
       "comment" : commentTxt.value
    };

    ajaxAPI("/board/comment/insert", jsonData, "POST").then(response => {

        window.location.href = '/board/view?no=' + no.value;
    })
}



/* 댓글 내용 수정 */
function fnbtnUpdateComment(cno) {

    let reply_comment = document.getElementById("reply_comment" + cno); // 원래 있던 댓글
    let reply_update = document.getElementById("reply_update" + cno); // 원래 있던 거 사라진 후 댓글 작성할거
    let btn_co_update = document.getElementById("btn_co_update" + cno); // 댓글 수정 버튼
    let btn_co_delete = document.getElementById("btn_co_delete" + cno); // 댓글 삭제 버튼
    let btn_co_save = document.getElementById("btn_co_save" + cno); // 수정 버튼 후 저장버튼
    let btn_co_cancel = document.getElementById("btn_co_cancel" + cno); // 수정 버튼 후 취소버튼


    /* 버튼 스타일 */
    reply_comment.style.display = "none";
    btn_co_update.style.display = "none";
    btn_co_delete.style.display = "none";
    reply_update.style.display = "";
    btn_co_save.style.display = "";
    btn_co_cancel.style.display = "";


    /* 수정 댓글 등록하기 (수정 버튼 후 저장버튼)*/
    btn_co_save.addEventListener("click", function() {

        let jsonData = {
            "cno" : cno,
            "comment" : reply_update.value
        }


        ajaxAPI("/board/comment/update", jsonData, "POST").then(response => {
            window.location.href = '/board/view?no=' + no.value;
        })
    })


    /* 취소버튼 클릭 (수정 버튼 후 취소버튼)*/
    btn_co_cancel.addEventListener("click", function() {

        reply_comment.style.display = "";
        btn_co_update.style.display = "";
        btn_co_delete.style.display = "";
        reply_update.style.display = "none";
        btn_co_save.style.display = "none";
        btn_co_cancel.style.display = "none";

        reply_update.value = reply_comment.textContent;


    })




}

/* 댓글 삭제 */
function fnbtnDeleteComment(cno){

    console.log("fnbtnDeleteComment");
    console.log("cno: " + cno);

    if(confirm("삭제 하시겠습니까?")){

        let jsonData = {
            "cno" : cno
        }

        ajaxAPI("/board/comment/delete", jsonData, "POST").then(response => {
            console.log("success");
            window.location.href = '/board/view?no=' + no.value;
        })
    }

}