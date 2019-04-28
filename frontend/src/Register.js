import React, {Component} from "react";
import {withRouter} from "react-router-dom";
import './css/Login_style.css';

class Register extends Component {
    constructor() {
        super();
        this.state = {errorMessage: '', username: '', password: '', passwordAgain: ''}
        this.handleChange = this.handleChange.bind(this);
        this.registerUser = this.registerUser.bind(this);
    }

    handleChange(event) {
        let value = event.target.value;
        let name = event.target.name;
        this.setState({[name]: value})
    }

    registerUser(event) {
        if (this.state.password !== this.state.passwordAgain) {
            this.setState({errorMessage: <div className="loginErrorMessage">Given passwords do not match.</div>});
        } else {
            let userInformation = {username: this.state.username, password: this.state.password, userType: 1};

            fetch('/api/users/', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userInformation)
            }).then((response) => response.json()).then((user) => {
                if (user.status === 400) {
                    this.setState({errorMessage: <div className="loginErrorMessage">This username is already in use.</div>});
                } else {
                    localStorage.setItem('loggedin', 'true');
                    localStorage.setItem('userId', user.id);
                    localStorage.setItem('userType', user.userType);
                    localStorage.setItem('username', user.username);
                    this.props.history.push('/');
                }
            })
        }
    }

    render() {
        let classn = window.innerWidth >= 640 ? "desktop" : "mobile";
        return (
            <div className={"loginContainer " + classn}>
            <h1 className="loginH1">Register</h1>
            <input name="username" type="text" className="textInput" placeholder="Username" onChange={this.handleChange} />
            <br/><input name="password" type="password" className="textInput" placeholder="Password" onChange={this.handleChange}  />
            <br/><input name="passwordAgain" type="password" className="textInput" placeholder="Password again" onChange={this.handleChange}  />
            <br/><button type="submit" onClick={this.registerUser} className="buttonInput">Register</button>

            {this.state.errorMessage}
        </div>);
    }
}

export default withRouter(Register);