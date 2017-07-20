'use strict';

import React, {Component} from "react";
import {AutoComplete} from "material-ui";
import getMuiTheme from "material-ui/styles/getMuiTheme";
import MuiThemeProvider from "material-ui/styles/MuiThemeProvider";
import injectTapEventPlugin from "react-tap-event-plugin";
const client = require('./client');
const ReactDOM = require('react-dom');

injectTapEventPlugin();

class SearchBarWithAutoComplete extends Component {
    constructor(props) {
        super(props);
        this.onUpdateInput = this.onUpdateInput.bind(this);
        this.onNewRequest = this.onNewRequest.bind(this);
        this.state = {
            dataSource: [],
            inputValue: ''
        }
    }

    performSearch() {
        client({method: 'GET', path: '/api/autocomplete' + this.getInputValue()})
            .done(response => {
                console.log(response);
                this.setState({dataSource: response.entity.keys});
            });
    }

    onUpdateInput(inputValue) {
        const self = this;

        this.setState({
            inputValue: inputValue
        }, function () {
            self.performSearch();
        });
    }

    onNewRequest(searchTerm) {
        const self = this;
        this.performSearch();
    }

    render() {
        return <MuiThemeProvider muiTheme={getMuiTheme()}>
            <AutoComplete
                searchText={this.state.inputValue}
                floatingLabelText={this.props.placeHolder}
                filter={AutoComplete.noFilter}
                openOnFocus={true}
                dataSource={this.state.dataSource}
                onUpdateInput={this.onUpdateInput}
                onNewRequest={this.onNewRequest}/>
        </MuiThemeProvider>
    }

    getInputValue() {
        return this.state.inputValue ? '/' + this.state.inputValue : ''
    }
}
export default SearchBarWithAutoComplete;

ReactDOM.render(
    <SearchBarWithAutoComplete
        placeHolder="Search Json"
    />,
    document.getElementById('react')
);

