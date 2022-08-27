ClassicEditor.create(document.querySelector("#editor"), {
    language: "ko", // 언어 설정
})
    .then((editor) => {
        console.log(editor);
    })
    .catch((error) => {
        console.error(error);
    });
