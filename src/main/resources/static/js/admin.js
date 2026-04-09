// FAQ 전체 조회
function loadFaqs() {
    fetch('/api/faq')
        .then(response => response.json())
        .then(data => {
            const faqList = document.getElementById('faqList');
            faqList.innerHTML = '';

            data.forEach((faq, index) => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${index + 1}</td>
                    <td>${faq.question}</td>
                    <td>${faq.answer}</td>
                    <td>
                        <button class="btn-edit" onclick="editFaq(${faq.id}, '${faq.question}', '${faq.answer}')">수정</button>
                        <button class="btn-delete" onclick="deleteFaq(${faq.id})">삭제</button>
                    </td>
                `;
                faqList.appendChild(tr);
            });
        });
}

// FAQ 등록
function createFaq() {
    const question = document.getElementById('newQuestion').value.trim();
    const answer = document.getElementById('newAnswer').value.trim();

    if (!question || !answer) {
        alert('질문과 답변을 모두 입력하세요.');
        return;
    }

    fetch('/api/faq', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ question, answer })
    })
    .then(() => {
        document.getElementById('newQuestion').value = '';
        document.getElementById('newAnswer').value = '';
        loadFaqs();
    });
}

// FAQ 수정
function editFaq(id, question, answer) {
    const newQuestion = prompt('질문 수정', question);
    if (!newQuestion) return;

    const newAnswer = prompt('답변 수정', answer);
    if (!newAnswer) return;

    fetch(`/api/faq/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ question: newQuestion, answer: newAnswer })
    })
    .then(() => loadFaqs());
}

// FAQ 삭제
function deleteFaq(id) {
    if (!confirm('정말 삭제하시겠습니까?')) return;

    fetch(`/api/faq/${id}`, {
        method: 'DELETE'
    })
    .then(() => loadFaqs());
}

// 페이지 로드 시 FAQ 목록 불러오기
document.addEventListener('DOMContentLoaded', loadFaqs);