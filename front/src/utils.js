import { humanizer } from "humanize-duration";

export function extractDate(d) {
  d.setHours(10);
  return d.toISOString().split("T")[0];
}

export async function doRequest(reqPromise, messages) {
  try {
    const res = await reqPromise;
    if (200 <= res.status && res.status < 300) {
      return [res.data || true, messages[res.status] || ""];
    } else if (res.status === 401) {
      return [null, "Authentification requise !"];
    } else {
      return [null, messages[res.status] || "Erreur inconnue"];
    }
  } catch (e) {
    console.warn("Request Error: ", e);
    return [null, messages.error || "Erreur serveur"];
  }
}

const frenchHumanizer = humanizer({
  language: "fr",
});
export const humanizeDuration = frenchHumanizer;

export function normalized(str) {
  return str
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "")
    .toLowerCase();
}
