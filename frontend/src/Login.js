import React, {Component} from "react";
import './css/Login_style.css';

class Login extends Component {
    render() {
        return (<div className="loginContainer">
            <h1>Login</h1>
            <form>
                <input type="text" className="textInput" placeholder="Username" />
                <br/><input type="text" className="textInput" placeholder="Password"  />
                <br/><button type="submit" className="buttonInput">Login</button>
            </form>

            <p>No user? Register <a href="/register">here</a>!</p>
        </div>);
    }
}

export default Login;