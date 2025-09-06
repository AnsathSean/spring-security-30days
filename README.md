# Spring Security 30Days

「站住 口令 誰」—— 資安權限與授權的觀念教學，以 Spring Boot Security 實作

---

## 專案說明 (Description)

這是一個關於 **認證 (Authentication) 與授權 (Authorization)** 的完整教學專案，文章發布於2025年it鐵人賽中，這裡是存放相關Reop的地方

本次專案以 Java 的**Spring Boot Security** 為基礎，設計成 **30 天系列文章**。  

本專案的目標是讓讀者從 **最基礎的 Basic Auth** 開始，  
一路理解 **Session / Token / JWT / OAuth2 / OIDC / SAML** 的概念，  
最終完成一個具備 **RBAC + ABAC + Scope** 的「權限系統雛型」。  

特色：
- 每個 branch 對應到課程中的一個成果階段  
- 程式碼保持簡潔（盡量少依賴外部服務）  
- 適合初學者與進階讀者逐步學習  

---

##  Branch 對照表

| Branch 名稱 | 對應內容 | 說明 |
|-------------|----------|------|
| `day04-basic-auth` | Basic Authentication | 最簡單的 in-memory 帳號密碼驗證 |
| `day06-cookie-session` | Session (Cookie Based Auth) | 使用 JSESSIONID 驗證登入狀態 |
| `day10-ManualJWT` | JWT 整合 Spring Security | 登入 API → 發 JWT → Filter 驗證 |
| `day12-jwt-security` | JWT 整合 Spring Security | 登入 API → 發 JWT → Filter 驗證 |
| `day20-oidc-google-login` | OIDC (Google 登入) | 使用 Google OIDC 登入並驗證 ID Token |
| `day27-case1-jwt-oidc` | 專案案例 Part 1 | 本地帳號 (JWT) + Google OIDC 整合 |
| `day28-case2-rbac-abac` | 專案案例 Part 2 | 加入授權控制 (RBAC + ABAC) |
| `day29-case3-advanced-authorization` | 專案案例 Part 3 | 多層授權 (Scope + Role + Attribute) 與企業級思維 |
