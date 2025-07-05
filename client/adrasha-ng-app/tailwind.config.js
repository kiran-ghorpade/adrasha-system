/** @type {import('tailwindcss').Config} */
module.exports = {
  corePlugins: {
    preflight: false, // Disable Tailwind's base styles
  },
  content: ["./src/**/*.{html,ts}"],
  safelist: [
    "bg-green-600",
    "bg-red-600",
    "bg-yellow-500",
    "bg-blue-600",
    "text-white",
    "text-black",
    "px-4",
    "py-2",
    "rounded",
    "shadow-md",
  ],
  theme: {
    extend: {},
  },
  plugins: [],
};
