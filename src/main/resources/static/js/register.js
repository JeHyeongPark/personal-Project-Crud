"use strict";

let register_btn = document.getElementById('regist_button');        // 회원가입 버튼
let postcode_btn =  document.getElementById('post_code');           // 우편번호 버튼
let regist_postcode = document.getElementById('regist_postcode');   // 우편번호 적는곳
let regist_addr =document.getElementById("regist_addr");            // 기본주소 적는곳

document.addEventListener('DOMContentLoaded', function() {
    addEventListnerCRUDBtn();

});


function addEventListnerCRUDBtn(){
    register_btn.addEventListener('click', fnRegist);
    postcode_btn.addEventListener('click', fnPostcode);
}



/* 우편번호 버튼 */
function fnPostcode(){
    new daum.Postcode({
        oncomplete: function(data) {
            regist_postcode.value = data.zonecode;
            regist_addr.value = data.address;
        }
    }).open();



}






/* 회원가입 버튼 */
function fnRegist() {
    let uid = document.getElementById('regist_id').value;
    let pw = document.getElementById('regist_pw').value;
    let pwc = document.getElementById('regist_pwc').value;
    let name = document.getElementById('regist_name').value;
    let birth = document.getElementById('regist_birth').value;
    let hp = document.getElementById('regist_hp').value;
    let postcode = document.getElementById('regist_postcode').value
    let addr1 = document.getElementById('regist_addr').value;
    let addr2 = document.getElementById('regist_addr2').value;

    let check_name = document.getElementById("check_name");
    let check_name2 = document.getElementById("check_name2");
    let check_birth = document.getElementById("check_birth");
    let check_birth2 = document.getElementById("check_birth2");
    let check_ph = document.getElementById("check_ph");
    let check_ph2 = document.getElementById("check_ph2");
    let check_pw = document.getElementById("check_pw");
    let check_pw2 = document.getElementById("check_pw2");
    let check_id1 = document.getElementById("check_id1");
    let check_id2 = document.getElementById("check_id2");
    let check_post = document.getElementById("check_post");

    let isValid = true;


    if (uid === "") {
        check_id2.style.display = '';
        isValid = false;
    } else {
        check_id2.style.display = 'none';
    }


    if (!/^[a-zA-Z0-9]+$/.test(uid) || uid.length < 6 || uid.length > 12) {
        check_id1.style.display = '';
    } else {
        check_id1.style.display = 'none';
    }


    if (pw === "") {
        check_pw.style.display = '';
        isValid = false;
    } else {
        check_pw.style.display = 'none';
    }

    if (!/^[a-zA-Z0-9]+$/.test(pw) || pw.length < 6 || pw.length > 12){
        check_pw2.style.display = '';
        isValid = false;
    } else {
        check_pw2.style.display = 'none';
    }


    if (pw !== pwc) {
        check_pwc.style.display = '';
        isValid = false;
    } else {
        check_pwc.style.display = 'none';
    }

    if (name === "") {
        check_name.style.display = '';
        isValid = false;
    } else {
        check_name.style.display = 'none';
    }


    if (!/^[a-zA-Z가-힣]+$/.test(name)){
        check_name2.style.display = '';
        isValid = false;
    } else {
        check_name2.style.display = 'none';
    }



    if (birth === "") {
        check_birth.style.display = '';
        isValid = false;
    } else {
        check_birth.style.display = 'none';
    }

    if (!/^[0-9]{8}$/.test(birth)) {
        check_birth2.style.display = '';
        isValid = false;
    } else {
        check_birth2.style.display = 'none';
    }

    if (hp === "") {
        check_ph.style.display = '';
        isValid = false;
    } else {
        check_ph.style.display = 'none';
    }


    if (!/^[0-9]{11}$/.test(hp)) {
        check_ph2.style.display = '';
        isValid = false;
    } else {
        check_ph2.style.display = 'none';
    }


    if (postcode === "") {
        check_post.style.display = '';
        isValid = false;
    } else {
        check_post.style.display = 'none';
    }

    if (isValid && confirm("가입 하시겠습니까?")) {
        let jsonData = {
            "uid": uid,
            "pass": pw,
            "name": name,
            "birth": birth,
            "hp": hp,
            "zip": postcode,
            "addr1": addr1,
            "addr2": addr2
        };

        ajaxAPI("/user/register", jsonData, "POST").then(response => {
            alert("가입 되었습니다.");
            window.location.href = '/';
        });
    }
}


