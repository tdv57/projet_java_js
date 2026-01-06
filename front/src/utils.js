export function extractDate(d) {
  d.setHours(10);
  return d.toISOString().split("T")[0];
}

export async function doRequest(resPromise, messages) {
  try {
    const res = await resPromise;
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
