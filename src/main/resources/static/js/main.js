function sendMessage() {
    const input = document.getElementById('questionInput');
    const question = input.value.trim();

    if (!question) return;

    // 사용자 메시지 출력
    appendMessage('user', '나', question);
    input.value = '';

    // API 호출
    fetch('/api/chat', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ question: question })
    })
    .then(response => response.json())
    .then(data => {
        appendMessage('bot', 'AI 민원 안내', data.answer);
    })
    .catch(error => {
        appendMessage('bot', 'AI 민원 안내', '오류가 발생했습니다. 다시 시도해 주세요.');
    });
}

function appendMessage(type, name, text) {
    const chatBox = document.getElementById('chatBox');

    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message', type);

    const nameDiv = document.createElement('div');
    nameDiv.classList.add('name');
    nameDiv.textContent = name;

    const bubbleDiv = document.createElement('div');
    bubbleDiv.classList.add('bubble');
    bubbleDiv.textContent = text;

    messageDiv.appendChild(nameDiv);
    messageDiv.appendChild(bubbleDiv);
    chatBox.appendChild(messageDiv);

    // 스크롤 아래로
    chatBox.scrollTop = chatBox.scrollHeight;
}

// 엔터키로 전송
document.getElementById('questionInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        sendMessage();
    }
});