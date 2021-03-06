import React, {Component} from "react";
import {withRouter} from "react-router-dom";
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
        let userInformation = {username: this.state.username, password: this.state.password};

        fetch('/api/users/login', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userInformation)
        }).then((response) => response.json()).then((user) => {
            if (user.status === 400) {
                this.setState({errorMessage: <div className="loginErrorMessage">Username or password is incorrect.</div>});
            } else if (user.userType === 'DELETED') {
                this.setState({errorMessage: <div className="loginErrorMessage">This user has been deleted. Site access denied.</div>});
            } else {
                localStorage.setItem('loggedin', 'true');
                localStorage.setItem('userId', user.id);
                localStorage.setItem('userType', user.userType);
                localStorage.setItem('username', user.username);
                this.props.history.push('/');
            }
        })
    }

    render() {
        let classn = window.innerWidth >= 640 ? "desktop" : "mobile";
        return (<div className={"loginContainer" + classn}>
            <h1 className="loginH1">Login</h1>
            <input name="username" type="text" className="textInput" placeholder="Username" onChange={this.handleChange} />
            <br/><input name="password" type="password" className="textInput" placeholder="Password" onChange={this.handleChange}  />
            <br/><button type="submit" onClick={this.logUserIn} className="buttonInput">Login</button>

            {this.state.errorMessage}

            <p>Not a user? Register <a href="/register">here</a>!</p>
        </div>);
    }
}

export default withRouter(Login);