import React, {Component} from "react";
import './css/Login_style.css';

class Login extends Component {
    constructor(props) {
        super(props);
        this.logUserIn = this.logUserIn.bind(this);
        this.handleChange = this.handleChange.bind(this);
        let loggedin = false;

        if (localStorage.getItem('loggedin') === true) {
            loggedin = true;
        }

        this.state = {loggedin: loggedin
            , errorMessage: ''
            , username: ''
            , password: ''}
    }

    handleChange(event) {
        let value = event.target.value;
        let name = event.target.name;
        this.setState({[name]: value})
    }

    logUserIn() {
        //this.setState({errorMessage: <div className="loginErrorMessage">Username or password is incorrect.</div>});
        let userInformation = {username: this.state.username, password: this.state.password};

        fetch('/api/users/login', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userInformation)
        }).then((response) => response.json()).then((id) => console.log(id))
    }

    render() {
        return (<div className="loginContainer">
            <h1>Login</h1>
            <input name="username" type="text" className="textInput" placeholder="Username" onChange={this.handleChange} />
            <br/><input name="password" type="password" className="textInput" placeholder="Password" onChange={this.handleChange}  />
            <br/><button type="submit" onClick={this.logUserIn} className="buttonInput">Login</button>

            {this.state.errorMessage}

            <p>Not a user? Register <a href="/register">here</a>!</p>
        </div>);
    }
}

export default Login;