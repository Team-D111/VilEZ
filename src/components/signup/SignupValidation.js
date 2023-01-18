// Login 유효성 검사하는 함수
// input name을 인자로 받아서, 각 input의 유효성 검사 조건과 에러 문구를 설정

const SignupValidation = ({ email, password, password2, nickname }) => {
  const errors = {};

  if (!email) {
    errors.email = "이메일을 입력해주세요.";
  } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(email)) {
    errors.email = "올바른 이메일 형식이 아닙니다. 다시 입력해주세요.";
  }

  if (!password) {
    errors.password = "비밀번호를 입력해주세요.";
  } else if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/i.test(password)) {
    errors.password = "영어 소문자, 숫자 조합 8~16자리로 입력해주세요.";
  }

  if (!password2) {
    errors.password2 = "비밀번호를 다시 한 번 입력해주세요.";
  } else if (password2 !== password) {
    errors.password2 = "일치하지 않습니다. 다시 입력해주세요.";
  }

  if (!nickname) {
    errors.nickname = "닉네임을 입력해주세요.";
  } else if (nickname.length > 6) {
    errors.nickname = "최대 6자까지 설정할 수 있어요.";
  }

  return errors;
};

export default SignupValidation;
