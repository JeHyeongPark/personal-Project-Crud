"use strict";

let no = document.getElementById("no");
let btnSave = document.getElementById('btnSave')  // 글 저장 버튼
let tempContent = document.getElementById("editorTxt");
let btnFileDelete = document.getElementById("delete_file");
let fileupload = document.getElementById("fileupload");
let fno = fileupload.getAttribute("data-fno");


let oEditor = []; // 에디터 선언

document.addEventListener('DOMContentLoaded', function() {
    appendEditorHtml();
    addEventListnerCRUDBtn();
});



function fnSave() {

    let title = document.getElementById('title').value;                              // 글 제목
    oEditor.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []);                           // 에디터 글 내용 가져오기
    let content = document.getElementById("editorTxt").value;                        // 글 내용
    let contentValidate =  oEditor.getById["editorTxt"].getContents();                        // innerHTML을 사용하여 HTML 내용 가져오기
    let fileUpload = document.getElementById("fileupload");            // 파일 업로드
    let formData = new FormData();
    //  파일 업로드를 위한 폼 생성
    let selectedFile = fileUpload.files[0];                                                   // 업로드한 파일


    let jsonData = {
    };

    if(title === ""){
        alert("제목을 입력해 주세요.");
        return;
    }

    // HTML 태그 및 공백 제거 후 내용 확인
    let strippedContent = contentValidate.replace(/[<][^>]*[>]/g, "").replace(/&nbsp;/g, "");

    if(strippedContent.trim() === "" || strippedContent == null) {
        alert("내용을 입력해 주세요.");
        return;
    }

    if(!selectedFile) {
        jsonData = {
            "no" : no.value,
            "title": title,
            "content": content,
            "fno" : fno
        };
    }else {
        jsonData = {
            "no" : no.value,
            "title": title,
            "content": content,
            "fno" : fno,
            "name":selectedFile.name
        };
    }


    if (confirm("수정 하시겠습니까?")) {


        ajaxAPI("/board/modify/update", jsonData, "POST").then(response => {

            let no = response.no;

            if (selectedFile !== null && selectedFile !== undefined) {
                formData.append("file", selectedFile);    // 폼에 파일 추가
                formData.append("oriName", selectedFile.name);


                /* 파일 등록 */
                $.ajax({
                    url: '/board/upload',
                    processData: false,
                    contentType: false,
                    data: formData,
                    type: 'POST',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (result) {
                        alert("수정 되었습니다.");
                        let url = '/board/view?no=' + no;
                        window.location.href = url;
                    }
                })
            } else {
                let url = '/board/view?no=' + no;
                window.location.href = url;
            }
        });

    }

}



function toggleFileElements() {
    // 파일 업로드 input 요소 가져오기
    let fileUpload = document.getElementById("fileupload");
    // 파일 업로드 input 요소의 style.display 값 확인하여 토글
    fileUpload.style.display = (fileUpload.style.display === 'none') ? 'block' : 'none';

    // 숨겨진 div 요소들 가져오기
    let listFile = document.getElementById("list_file");
    let deleteFile = document.getElementById("delete_file");

    // 숨겨진 div 요소들의 style.display 값 변경
    listFile.style.display = 'none';
    deleteFile.style.display = 'none';
}




/*에디터*/
function appendEditorHtml() {
    /** 1) 에디터 설정 */
    // nhn.husky.EZCreator.createInIFrame 함수를 호출하여 에디터를 생성합니다.
    nhn.husky.EZCreator.createInIFrame({
        // 모드 변경기를 사용하지 않습니다. (WYSIWYG 및 HTML 모드를 전환할 수 있는 기능)
        bUseModeChanger: false,
        // 세로 리사이저를 사용하지 않습니다. (에디터 높이 조정 기능)
        bUseVerticalResizer: false,
        // 에디터의 객체 참조를 설정합니다.
        oAppRef: oEditor,
        // 에디터를 삽입할 영역의 ID를 지정합니다.
        elPlaceHolder: 'editorTxt',
        // 에디터의 스킨 파일 경로를 설정합니다.
        sSkinURI: '/smarteditor/SmartEditor2Skin.html',
        // 에디터의 생성자 함수를 지정합니다.
        fCreator: 'createSEditor2',
        htParams : {
            // 추가 글꼴 목록을 설정합니다.
            aAdditionalFontList : [["Noto Sans KR", "Noto Sans"]],
            // 기본 글꼴 설정을 지정합니다
            SE2M_FontName : {
                htMainFont: {'id': '돋움','name': '돋움', 'url': '','cssUrl': ''}
            },
            // 페이지 이동 시 확인창이 나오지 않도록 처리하는 함수를 설정합니다.
            fOnBeforeUnload : function() { }
        },
        // 에디터 로딩이 완료된 후 실행되는 콜백 함수입니다.
        fOnAppLoad: function() {
            // 'editorTxt' 영역에 기본 글꼴을 설정합니다. (글꼴: "Noto Sans KR", 크기: 11)
            oEditor.getById['editorTxt'].setDefaultFont( "돋움", 11);
            // 'editorTxt' 영역의 내용을 초기화합니다.
            oEditor.getById["editorTxt"].exec("SET_IR", [""]);
            // 'editorTxt' 영역에 미리 저장해둔 내용을 설정합니다.
            oEditor.getById["editorTxt"].exec("PASTE_HTML", [tempContent.value]); //내용밀어넣기
        }
    });

}

function addEventListnerCRUDBtn(){
    btnSave.addEventListener('click', fnSave);
    btnFileDelete.addEventListener('click', toggleFileElements)
}

