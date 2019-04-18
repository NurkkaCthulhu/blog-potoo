import React, {Component} from "react";
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
        }
    }

    render() {
        return (<div className="loginContainer">
            <h1 className="loginH1">Register</h1>
            <input name="username" type="text" className="textInput" placeholder="Username" onChange={this.handleChange} />
            <br/><input name="password" type="password" className="textInput" placeholder="Password" onChange={this.handleChange}  />
            <br/><input name="passwordAgain" type="password" className="textInput" placeholder="Password again" onChange={this.handleChange}  />
            <br/><button type="submit" onClick={this.registerUser} className="buttonInput">Register</button>

            {this.state.errorMessage}
        </div>);
    }
}

export default Register;