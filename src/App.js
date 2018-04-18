import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import {Col, Grid, Row, Table} from 'react-bootstrap';

const Months = Object.freeze({
                                 Jan: 0,
                                 Feb: 1,
                                 Mar: 2,
                                 Apr: 3,
                                 May: 4,
                                 Jun: 5,
                                 Jul: 6,
                                 Aug: 7,
                                 Sep: 8,
                                 Oct: 9,
                                 Nov: 10,
                                 Dec: 11
                             });

class App extends Component {
    constructor(props) {
        var i = 1;
        super(props);
        this.state = {
            currentMonth: Months.Apr,
            indicators: [
                {
                    name: 'Test',
                    history: [
                        {
                            month: Months.Apr,
                            // values: Array.from({length: 30}, () => Math.floor(Math.random() * 40)),
                            values: Array.from({length: 30}, () => i++),
                        },
                    ]
                },
            ],
        }
    }

    render() {
        return (
            <Grid>
                <Row className="show-grid">
                    <Col xs={12} md={8}>
                        <IndicatorsList indicators={this.state.indicators} currentMonth={this.state.currentMonth}/>
                    </Col>
                </Row>
            </Grid>
        );
    }
}

class IndicatorsList extends Component {
    render() {
        const indicators = this.props.indicators.map(
            indicator => <Indicator{...indicator} currentMonth={this.props.currentMonth} key={indicator.name}/>);
        return (
            <div>
                <h1>Indicators</h1>
                <Table striped bordered condensed hover>
                    <MonthHeader month={this.props.currentMonth} name='Indicators'/>
                    <tbody>
                    {indicators}
                    </tbody>
                </Table>
            </div>
        );
    }
}

function MonthHeader(props) {
    const daysInMonth = new Date(2018, props.month, 0).getDate();
    const dateTimeFormatter = Intl.DateTimeFormat('en-GB', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    });
    var dates = [];
    for (var i = 1; i < daysInMonth; i++) {
        dates.push(<th key={i} class="rotate"><div>{dateTimeFormatter.format(new Date(2018, props.month, i))}</div></th>);
    }
    return (
        <thead>
        <tr>
            <th>{props.name}</th>
            {dates}
        </tr>
        </thead>
    );
}

function Indicator(props) {
    const daysInMonth = new Date(2018, props.currentMonth, 0).getDate();

    let values = props.history.filter(history => history.month === props.currentMonth)[0].values.slice(0, daysInMonth);
    // console.log(values);
    // console.log(props.currentMonth);
    let ths = values.map(value => {
        return (<td>{value}</td>);
    });
    // console.log(ths);
    return (
        <tr>
            <td>{props.name}</td>
            {ths}
        </tr>
    );
}

export default App;
