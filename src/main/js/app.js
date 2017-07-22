'use strict';

const searchBar = require('./searchBar');
const upload = require('./upload');
import React, {Component} from "react";
import SearchBarWithAutoComplete from "./searchBar";
import getMuiTheme from "material-ui/styles/getMuiTheme";
import MuiThemeProvider from "material-ui/styles/MuiThemeProvider";
import {deepPurple400, tealA700} from "material-ui/styles/colors";
import Toggle from "material-ui/Toggle";
const ReactDOM = require('react-dom');

class App extends Component {

    constructor(props) {
        super(props);
        this.onSearchComplete = this.onSearchComplete.bind(this);
        this.onToggle = this.onToggle.bind(this);
        this.toggleText = this.toggleText.bind(this);
        this.displayMessage = this.displayMessage.bind(this);
        this.state = {
            jsonValue: '',
            jsonPath: '',
            keys: [],
            reverse: false,
            message: ''
        }
    }

    onToggle(event, checked) {
        this.setState({
            reverse: checked,
            jsonValue: '',

        });
    }

    toggleText() {
        return this.state.reverse ? "Search by Value" : "Search By Key";
    }

    onSearchComplete(results) {
        this.setState({
            jsonValue: results.jsonValue,
            jsonPath: results.jsonPath,
            keys: results.keys ? results.keys : [],
            message: results.message ? results.message : ''
        })
    }

    createResultFormat(jsonValue, jsonPath) {

        return <div>
            <br/>
            Value at {jsonPath}:
            <br />
            <div>
                <hr/>
                <br />
            </div>
            <pre>{JSON.stringify(jsonValue, null, 2) }</pre>
        </div>;

    }

    displayMessage(keys, searchTerm) {
        return keys.length > 0 ? "Keys with " + searchTerm + ":" : this.state.message
    }

    createResultFormatKeys(searchTerm, keys) {

        return <div>
            <br/>
            {this.displayMessage(keys, searchTerm)}
            <br />
            <div>
                <hr/>
                <ul>
                    {
                        keys.map(key => <li>{key}</li>)
                    }
                </ul>
                <br />
            </div>

        </div>;

    }

    render() {
        const self = this;
        let result = this.state.jsonValue;
        let path = this.state.jsonPath;
        let keys = this.state.keys;
        let renderResult;
        if (result) {
            renderResult = this.createResultFormat(result, path)
        } else if (this.state.reverse) {
            renderResult = this.createResultFormatKeys(path, keys)
        }
        return (
            <div>
                <div className="container">
                    <div id="main">
                        <h1>React Json Searching</h1>
                    </div>

                </div>
                <br />
                <br />
                <div id="box">
                    <div>
                        <SearchBarWithAutoComplete
                            placeHolder="Search For Json..."
                            callback={self.onSearchComplete}
                            reverse={self.state.reverse}
                        />
                        <MuiThemeProvider muiTheme={getMuiTheme(
                            {
                                palette: {
                                    primary1Color: deepPurple400,
                                    secondary1Color: tealA700,
                                }
                            }
                        )}>
                            <div>
                                <Toggle
                                    label={this.toggleText()}
                                    onToggle={this.onToggle}
                                />
                            </div>
                        </MuiThemeProvider>

                        {renderResult}
                    </div>
                </div>
                {/*<JsonUploadButton/>*/}
            </div>
        )

    }
}

ReactDOM.render(
    <App/>,
    document.getElementById('react')
);