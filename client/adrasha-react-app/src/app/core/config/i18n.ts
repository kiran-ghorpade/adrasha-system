import i18n from "i18next";
import { initReactI18next } from "react-i18next";

// the translations
// (tip move them in a JSON file and import them,
// or even better, manage them separated from your code: https://react.i18next.com/guides/multiple-translation-files)
const resources = {
  en: {
    translation: {
      "Welcome": "Welcome to React and react-i18next"
    }
  },
  mr: {
    translation: {
      "Welcome": "React आणि react-i18next मध्ये आपले स्वागत आहे"
    }
  }
};

i18n
  .use(initReactI18next) // passes e-i18next
  .init({
    resources,
    lng: "mr", 
    interpolation: {
      escapeValue: false 
    }
  });

  export default i18n;