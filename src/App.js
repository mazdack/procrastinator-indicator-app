import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  constructor(props) {
      super(props);
      this.state = {
          indicators: [
              {
                  name: 'Test',
                  history: [
                      {
                          date: new Date(2018, 4, 16, 10, 0),
                          value: 50,
                      },
                  ]
              },
          ],
      }
  }

  render() {
    return (
        <IndicatorsList indicators={this.state.indicators} />
    );
  }
}

class IndicatorsList extends Component {
    render() {
        const indicators = this.props.indicators.map(indicator => <Indicator{...indicator} />);
        return (
            <div>
                <h1>Indicators</h1>
                {indicators}
            </div>
        );
    }
}

function Indicator(props) {
    return (
        <div>
            <div>
                <h3>{props.name}</h3>
            </div>
            <div>
                <table>
                    <th><td>Date</td><td>Value</td></th>
                    <tbody>
                        {props.history.map(h => <tr><td>{h.date.toDateString()}</td><td>{h.value}</td></tr>)}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default App;
