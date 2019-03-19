import React from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import './frontpage_style.css';
import BlogPost from './BlogPost';

function Index() {
    return <h1>Etusivu</h1>;
}

function App() {

        return (
            <Router>
                <div>
                    <nav className="header">
                        <Link to="/">Sivun logo</Link>
                    </nav>

                    <Route path="/" exact component={Index} />
                    <BlogPost id ={1} />
                    <BlogPost id ={1}/>
                    <BlogPost id ={1}/>
                    <BlogPost id ={1}/>
                    <div className="footer"><span>Blog Potoo, the blog of the future (2019)</span></div>
                </div>
            </Router>
        );
    }


export default App;
