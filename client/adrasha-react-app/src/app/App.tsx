import { router } from '@core/config';
import { CoreModule } from '@core/CoreModule';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { RouterProvider } from 'react-router-dom';
import './App.css';

function App() {
  return (
    <CoreModule>
        <RouterProvider router={router} />
        <ReactQueryDevtools initialIsOpen={false} />
    </CoreModule>
  );
}

export default App;
