// FAQ 전체 조회
function loadFaqs() {
    fetch('/api/faq')
        .then(response => response.json())
        .then(data => {
            const faqList = document.getElementById('faqList');
            faqList.innerHTML = '';

            data.forEach((faq, index) => {
                const tr = document.createElement('tr');
                tr.setAttribute('data-id', faq.id);
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
    const row = document.querySelector(`tr[data-id="${id}"]`);

    row.innerHTML = `
        <td>${row.cells[0].textContent}</td>
        <td><input type="text" id="editQuestion-${id}" value="${question}" style="width:100%; padding:6px; border:1px solid #d0d8e4; border-radius:4px;"/></td>
        <td><textarea id="editAnswer-${id}" style="width:100%; padding:6px; border:1px solid #d0d8e4; border-radius:4px; height:60px;">${answer}</textarea></td>
        <td>
            <button class="btn-edit" onclick="saveEdit(${id})">저장</button>
            <button class="btn-delete" onclick="loadFaqs()">취소</button>
        </td>
    `;
}

function saveEdit(id) {
    const question = document.getElementById(`editQuestion-${id}`).value.trim();
    const answer = document.getElementById(`editAnswer-${id}`).value.trim();

    if (!question || !answer) {
        alert('질문과 답변을 모두 입력하세요.');
        return;
    }

    fetch(`/api/faq/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ question, answer })
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