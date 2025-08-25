# Authentication Features Implementation

This document describes the implementation of the authentication TODOs that were completed.

## New Features

### 1. Refresh Token Support
- Added refresh token generation alongside access tokens
- Refresh tokens have longer expiration times (configurable)
- Refresh tokens are stored with versioning for invalidation

### 2. Token Versioning and Invalidation
- Implemented `TokenVersion` entity to track token versions
- Each token (access/refresh) has a unique version number
- Tokens can be individually invalidated or all tokens for a user can be invalidated

### 3. New Authentication Service Methods

#### logout()
- Invalidates all tokens for the currently authenticated user
- Uses Spring Security context to identify the current user

#### invalidate() 
- Invalidates all access and refresh tokens for the authenticated user
- Similar to logout but semantically different (could be used for security incidents)

#### refresh(RefreshTokenCommand)
- Validates the provided refresh token
- Generates new access and refresh tokens
- Invalidates the old refresh token
- Returns `RefreshTokenResult` with new tokens

## Configuration

Add these properties to your application configuration:

```properties
app.security.token.refresh-token.secret=your-refresh-token-secret
app.security.token.refresh-token.expiration=1209600000
```

## Database Schema

New table `token_versions`:
- `id` (UUID, Primary Key)
- `user_id` (UUID, Foreign Key to users)
- `version` (Long, Token version number)
- `token_type` (String, "ACCESS" or "REFRESH")
- `expires_at` (LocalDateTime)
- `is_invalidated` (Boolean)
- `invalidated_at` (LocalDateTime)
- `created_at` (LocalDateTime)
- `updated_at` (LocalDateTime)

## DTOs

### New Command
- `RefreshTokenCommand` - Contains refresh token for renewal

### New Result
- `RefreshTokenResult` - Contains new access and refresh tokens

### Modified Result
- `LoginResult` - Now includes both `accessToken` and `refreshToken`

## Security Considerations

1. **Token Versioning**: Each token has a version that must match the database record
2. **Invalidation**: Old tokens are marked as invalidated rather than deleted for audit purposes
3. **Separate Secrets**: Access and refresh tokens use different signing secrets
4. **Expiration**: Refresh tokens have longer expiration times than access tokens