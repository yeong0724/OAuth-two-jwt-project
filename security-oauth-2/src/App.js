import "./App.css";
import "./Button.css";

import naverBtn from "./naver_btn.png";

function App() {
  const onNaverLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/naver";
  };

  const getData = () => {
    fetch("http://localhost:8080/my", {
      method: "GET",
      credentials: "include",
      headers: {
        Accept: "application/json",
      },
    })
      .then((res) => {
        console.log("res", res);
        return res.json();
      })
      .then((data) => {
        alert(JSON.stringify(data, null, 2));
      })
      .catch((error) => alert(error));
  };

  return (
    <div className="App">
      <header>
        <div
          style={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            margin: "20px 0px",
            gap: "10px",
          }}
        >
          <button className="naver-login-btn" onClick={onNaverLogin}>
            <img src={naverBtn} alt="Naver logo" />
          </button>
          <button className="get-data-btn" onClick={getData}>
            Get Data
          </button>
        </div>
      </header>
    </div>
  );
}

export default App;
