<script lang="ts">
	import { error } from "@sveltejs/kit";

    function submitLoginForm(this : HTMLFormElement) {
        const form: HTMLFormElement = this;

        form.username.value = form.username.value.trim();

        if (form.username.value.length === 0) {
            alert('사용자 이름을 입력해주세요.');
            form.username.focus();
            return;
        }
        
        form.password.value = form.password.value.trim();

        if (form.password.value.length === 0) {
            alert('비밀번호를 입력해주세요.');
            form.password.focus();
            return;
        }

        fetch('http://localhost:8090/api/v1/members/login', {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: form.username.value,
                password: form.password.value
            })
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error(error);
            });
    }
</script>

<form class="flex gap-2" on:submit|preventDefault={submitLoginForm}>
    <input class="input input-bordered" type="text" name="username" placeholder="사용자 이름" />
    <input class="input input-bordered" type="password" name="password" placeholder="비밀번호" />
    <button class="btn btn-primary" type="submit">로그인</button>
</form>