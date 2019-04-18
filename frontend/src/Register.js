import React, {Component} from "react";
import './css/Login_style.css';

class Register extends Component {
    constructor() {
        super();
        this.state = {errorMessage: ''}
        this.handleChange = this.handleChange.bind(this);
        this.registerUser = this.registerUser.bind(this);
    }

    handleChange(event) {

    }

    registerUser(event) {

    }

    render() {
        return (<div className="loginContainer">
            <h1 className="loginH1">Register</h1>
            <input name="username" type="text" className="textInput" placeholder="Username" onChange={this.handleChange} />
            <br/><input name="password" type="password" className="textInput" placeholder="Password" onChange={this.handleChange}  />
            <br/><input name="password" type="password" className="textInput" placeholder="Password again" onChange={this.handleChange}  />
            <br/><button type="submit" onClick={this.registerUser} className="buttonInput">Register</button>

            {this.state.errorMessage}
        </div>);
    }
}

export default Register;