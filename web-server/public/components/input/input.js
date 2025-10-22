export class Input extends HTMLElement {
    constructor() {
        super(); // 프로토타입 체인 적용
        const shadow = this.attachShadow({ mode: "open" });
    }

    connectedCallback() {
        const variant = this.getAttribute('variant') || 'primary';
        const text = this.getAttribute('text') || '확인';
        //회원가입 페이지에서 사용할거니까 재사용하면 submit으로 전달해줘야함
        const type = this.getAttribute('type') || 'button';
        const disabled = this.hasAttribute('disabled');

        this.shadowRoot.innerHTML = `
            <style>
                .btn {
                    display: inline-flex;

                    height: 33px;
                    width: 314px;

                    align-items: center;
                    justify-content: center;
                    padding: 10px 20px;

                    border: none;
                    border-radius: 6px;

                    font-size: 16px;
                    font-weight: 600;
                    font-family: inherit;
                }

                .btn--primary {
                    background: #0348C9;
                    color: white;
                }

                .btn--danger {
                    background: #dc3545;
                    color: white;
                }

                .btn:disabled {
                    opacity: 0.5;
                    cursor: not-allowed;
                }

                .btn:active:not(:disabled) {
                    transform: scale(0.98);
                }
            </style>
            <button
                class="btn btn--${variant}"
                type="${type}"
                ${disabled ? 'disabled' : ''}
            >
                ${text}
            </button>
        `;

        // Shadow DOM 내부 버튼의 클릭을 외부로 전파
        const button = this.shadowRoot.querySelector('button');
        button.addEventListener('click', (e) => {
            // type이 submit이면 가장 가까운 form을 찾아서 submit
            if (type === 'submit') {
                const form = this.closest('form');
                if (form) {
                    e.preventDefault(); // 버튼의 기본 submit 방지
                    form.dispatchEvent(new Event('submit', { bubbles: true, cancelable: true }));
                }
            }

            // 외부에서 addEventListener로 감지할 수 있도록 click 이벤트 재전파
            this.dispatchEvent(new Event('click', { bubbles: true, composed: true }));
        });
    }
}

customElements.define("custom-input", Input);