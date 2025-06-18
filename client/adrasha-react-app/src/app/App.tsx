import { CoreModule } from '@core/CoreModule';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import './App.css';

function App() {
  return (
      <CoreModule>
        <ReactQueryDevtools initialIsOpen={false} />
      </CoreModule>
  );
}

export default App;
