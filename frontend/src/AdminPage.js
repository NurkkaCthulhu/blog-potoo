import React, {Component} from "react";
import ErrorPage from "./ErrorPage";
import './css/AdminPage_style.css';

class AdminPage extends Component {
    constructor() {
        super();
        this.state = {
            users: []
            , adminAccess: true
        }
    }

    componentDidMount() {
        if(localStorage.getItem('userType') === 'ADMIN') {
            fetch('/api/users')
                .then((httpResp) => httpResp.json())
                .then((users) => this.setState({users: users}));
        } else {
            console.log('not admin')
            this.setState({adminAccess: false});
        }
    }

    render() {
        return (
            <div>
                {this.state.adminAccess ?
                    <div className={"allUserscontainer"}>
                        <h1 className="manageTitle">Manage users</h1>
                        <table>
                            <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>Usertype</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.users.map((user) =>
                                    <tr key={user.id}>
                                        <td className="username">{user.username}</td>
                                        <td className="usertype">{user.userType}</td>
                                        <td className="deleteUser">Delete user</td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>
                    :
                    <ErrorPage message={"Access denied. This page is only for admins."}/>
                }
            </div>
        );
    }
}

export default AdminPage;